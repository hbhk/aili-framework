package org.hbhk.aili.support.server.zkclient;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class ZookeeperConfigurer extends PropertyPlaceholderConfigurer
		implements Watcher {
	private Logger log = LoggerFactory.getLogger(ZookeeperConfigurer.class);

	// 重写zookeeper中存储的配置
	private List<String> overrideLocaltions;

	public List<String> getOverrideLocaltions() {
		return overrideLocaltions;
	}

	public void setOverrideLocaltions(List<String> overrideLocaltions) {
		this.overrideLocaltions = overrideLocaltions;
	}

	/**
	 * 将source中的属性覆盖到dest属性中
	 * 
	 * @param dest
	 * @param source
	 */
	private void copyProperties(Properties dest, Properties source) {

		Enumeration<?> enums = source.propertyNames();

		while (enums.hasMoreElements()) {
			String key = (String) enums.nextElement();
			dest.put(key, source.get(key));
		}

	}

	private Properties queryOverrideLocation() {

		Properties props = new Properties();

		for (String location : overrideLocaltions) {

			PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
			try {
				Resource[] resource = pmrpr.getResources(location);

				Properties prop = PropertiesLoaderUtils
						.loadProperties(resource[0]);
				copyProperties(props, prop);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return props;

	}

	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties zkprops) throws BeansException {

		String zkhost = zkprops.getProperty("zk.host");
		String znodes = zkprops.getProperty("zk.cofing.root");
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(zkhost, 30000, this);
		} catch (IOException e) {
			log.error("Failed to connect to zk server" + zkhost, e);
			throw new ApplicationContextException(
					"Failed to connect to zk server" + zkhost, e);
		}

		try {
			for (String znode : znodes.split(",")) {
				List<String> children = zk.getChildren(znode.trim(), true);
				for (String child : children) {
					try {
						String value = new String(zk.getData(znode + "/"
								+ child, null, null));

						log.info("Zookeeper key:{}\t value:{}", child, value);
						zkprops.setProperty(child, value);
					} catch (Exception e) {
						log.error("Read property(key:{}) error", child);
						log.error("Exception:", e);
					}
				}
			}
		} catch (KeeperException e) {
			log.error("Failed to get property from zk server" + zkhost, e);
			throw new ApplicationContextException(
					"Failed to get property from zk server" + zkhost, e);
		} catch (InterruptedException e) {
			log.error("Failed to get property from zk server" + zkhost, e);
			throw new ApplicationContextException(
					"Failed to get property from zk server" + zkhost, e);
		} finally {
			try {
				zk.close();
			} catch (InterruptedException e) {
				log.error("Error found when close zookeeper connection.", e);
			}
		}

		Properties overProps = queryOverrideLocation();

		// 将扩展的properties信息覆盖zookeeper获取的属性
		copyProperties(zkprops, overProps);

		super.processProperties(beanFactoryToProcess, zkprops);
	}

	public void process(WatchedEvent event) {
	}

}
