package org.hbhk.aili.i18n.server.tag;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hbhk.aili.core.share.util.FileLoadUtil;
import org.springframework.core.io.Resource;

public class TagsCache {
	
	private static final Log logger = LogFactory
	.getLog(TagsCache.class);

	private static final String I18N_STRING = "i18n\\(\\s*['\"]([^'\"]+)['\"]";
	
	private static final String PERM_STRING = "isPermission\\(\\s*['\"]([^'\"]+)['\"]";
	
	private static TagsCache INSTANCE;
	
	//是否开启缓存，true开启，false未开启
	private boolean enableTagsCache = true;

	/**
	 * 保存所有缓存实例
	 */
	private final Map<String,Map<String,String>> caches = new ConcurrentHashMap<String, Map<String,String>>();

	/**
	 *  禁止从外部拿到实例
	  * 创建一个新的实例 TagsCache.
	  * @since 0.6
	 */
	private TagsCache() {
	}
	public void setEnableTagsCache(boolean enableTagsCache) {
		this.enableTagsCache = enableTagsCache;
	}

	public static TagsCache getInstance() {
		if(INSTANCE == null){
			INSTANCE = new TagsCache();
			logger.error("tagsCache is not config!");
		}
		return INSTANCE;
	}
	
	public boolean getEnableTagsCache(){
		if(TagsCache.getInstance()==null){
			return true;
		}else{
			return TagsCache.getInstance().enableTagsCache; 
		}
	}
	
	/**
	 * 根据得到模块内所有JS的国际化与权限信息
	 * getTagesInfo
	 * @param moduleName
	 * @return Map<String,String>
	 * @since:
	 */
	public Map<String, String> getTagesInfo(String moduleName){
		StringBuilder keySb = new StringBuilder();
		StringBuilder urlSb = new StringBuilder();
		StringBuilder tagsIdBuffer = new StringBuilder("tags_");
		tagsIdBuffer.append(moduleName);
		String tagsId = tagsIdBuffer.toString();
		Map<String,String> tagsInfo = caches.get(tagsId);
		if (!getEnableTagsCache() || tagsInfo == null) {
			try {
				Resource[] resources = FileLoadUtil.getResourcesForServletpath("/scripts/" + moduleName + "/**/*.js");
				Set<String> keySet = new HashSet<String>();
				Set<String> urlSet = new HashSet<String>();
				if (resources != null && resources.length > 0) {
					Pattern i18nPattern = Pattern.compile(TagsCache.I18N_STRING);
					Pattern permPattern = Pattern.compile(TagsCache.PERM_STRING);
					
					for (Resource resource : resources) {
						BufferedReader br = null;
						try {
							br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
							char[] charBuffer = new char[1];
							StringBuilder fileBuffer = new StringBuilder();
							while ((br.read(charBuffer)) > 0) {
								fileBuffer.append(charBuffer);
							}
							String fileString = fileBuffer.toString();
							Matcher im = i18nPattern.matcher(fileString);
							while (im.find()) {
								keySet.add(im.group(1));
							}
							Matcher pm = permPattern.matcher(fileString);
							while (pm.find()) {
								urlSet.add(pm.group(1));
							}
						} catch (Exception e) {
							logger.error(e.getMessage());
						} finally {
							if (br != null) {
								br.close();
							}
						}
	
					}
					for (String str : keySet) {
						keySb.append(str);
						keySb.append(",");
					}
					for (String str : urlSet) {
						urlSb.append(str);
						urlSb.append(",");
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			tagsInfo = new HashMap<String, String>();
			if (keySb.length() > 0) {
				tagsInfo.put("keys", keySb.substring(0, keySb.length() - 1));
			} else {
				tagsInfo.put("keys", "");
			}
			
			if (urlSb.length() > 0) {
				tagsInfo.put("urls", urlSb.substring(0, urlSb.length() - 1));
			} else {
				tagsInfo.put("urls", "");
			}
			caches.put(tagsId, tagsInfo);
		}
		return tagsInfo;
	}
	
	/**
	 * 通过缓存获取模块内groups中包含的所有JS的国际化及权限信息
	 */
	public Map<String,String> getTagesInfo(String moduleName, String[] groups) {
		Set<String> keySet = new HashSet<String>();
		Set<String> urlSet = new HashSet<String>();
		StringBuilder keySb = new StringBuilder();
		StringBuilder urlSb = new StringBuilder();
		StringBuilder tagsIdBuffer = new StringBuilder("tags_");
		tagsIdBuffer.append(moduleName);
		for(String group : groups){
			tagsIdBuffer.append("_");
			tagsIdBuffer.append(group);
		}
		String tagsId = tagsIdBuffer.toString();
		Map<String,String> tagsInfo = caches.get(tagsId);
		if (!getEnableTagsCache() || tagsInfo == null) {
			for(String group : groups){
				BufferedReader br = null;
				try {
					Properties wroProperties = WroResourcePropCache.getInstance().getWroResourceInfo(moduleName);
					group = wroProperties.getProperty(group+".js",group+".js");
					InputStream in = FileLoadUtil.getInputStreamForServletpath("/scripts/" + moduleName + "/**/"+group);
					Pattern i18nPattern = Pattern.compile(TagsCache.I18N_STRING);
					Pattern permPattern = Pattern.compile(TagsCache.PERM_STRING);
					br = new BufferedReader(new InputStreamReader(in));
					char[] charBuffer = new char[1];
					StringBuilder fileBuffer = new StringBuilder();
					while ((br.read(charBuffer)) > 0) {
						fileBuffer.append(charBuffer);
					}
					String fileString = fileBuffer.toString();
					Matcher im = i18nPattern.matcher(fileString);
					while (im.find()) {
						keySet.add(im.group(1));
					}
					Matcher pm = permPattern.matcher(fileString);
					while (pm.find()) {
						urlSet.add(pm.group(1));
					}
				}catch (FileNotFoundException e) {
					logger.error("wro mapping '"+group+".js' not found in this module");
				}catch (IOException e) {
					logger.error(e.getMessage());
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							logger.error(e.getMessage());
						}
					}
				}	
			}
			for (String str : keySet) {
				keySb.append(str);
				keySb.append(",");
			}
			for (String str : urlSet) {
				urlSb.append(str);
				urlSb.append(",");
			}
			tagsInfo = new HashMap<String, String>();
			if (keySb.length() > 0) {
				tagsInfo.put("keys", keySb.substring(0, keySb.length() - 1));
			} else {
				tagsInfo.put("keys", "");
			}
			
			if (urlSb.length() > 0) {
				tagsInfo.put("urls", urlSb.substring(0, urlSb.length() - 1));
			} else {
				tagsInfo.put("urls", "");
			}
			caches.put(tagsId, tagsInfo);
			
		}
		return tagsInfo;
	}
}