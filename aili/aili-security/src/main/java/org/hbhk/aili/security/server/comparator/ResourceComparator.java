package org.hbhk.aili.security.server.comparator;

import java.util.Comparator;

import org.hbhk.aili.security.share.pojo.ResourceInfo;

public class ResourceComparator implements Comparator<ResourceInfo> {

	@Override
	public int compare(ResourceInfo o1, ResourceInfo o2) {

		if (o1.getPriority() > o2.getPriority()) {
			return 1;
		}
		if (o1.getPriority() < o2.getPriority()) {
			return -1;
		}
		return 0;
	}

}