/**   */
package org.hbhk.aili.gen.server.test;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.hbhk.aili.orm.share.model.BaseInfo;

/**
 * 汽车品牌
 */
@Tabel("t_sct_car")
public class CarInfo extends BaseInfo {

	private static final long serialVersionUID = 5211504527985935662L;

	/** 品牌icon */
	@Column("icon")
	private String icon;

	/** 品牌主图 */
	@Column("main_img_url")
	private String mainImgUrl;

	/** 品牌中文名 */
	@Column("name")
	private String name;

	/** 品牌英文名 */
	@Column("name_en")
	private String nameEN;

	/** 品牌中文名全拼 */
	@Column("phoneTic")
	private String phoneTic;

	/** 品牌中文名简拼 */
	@Column("phoneTicShort")
	private String phoneTicShort;

	/** 品牌中文名首字母 */
	@Column("phoneTicFirst")
	private String phoneTicFirst;

	/** 品牌简介 */
	@Column("description")
	private String description;

	/** 品牌对应web页面地址 */
	@Column("url")
	private String url;

	/** 品牌官方网址 */
	@Column("website")
	private String website;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMainImgUrl() {
		return mainImgUrl;
	}

	public void setMainImgUrl(String mainImgUrl) {
		this.mainImgUrl = mainImgUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameEN() {
		return nameEN;
	}

	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}

	public String getPhoneTic() {
		return phoneTic;
	}

	public void setPhoneTic(String phoneTic) {
		this.phoneTic = phoneTic;
	}

	public String getPhoneTicShort() {
		return phoneTicShort;
	}

	public void setPhoneTicShort(String phoneTicShort) {
		this.phoneTicShort = phoneTicShort;
	}

	public String getPhoneTicFirst() {
		return phoneTicFirst;
	}

	public void setPhoneTicFirst(String phoneTicFirst) {
		this.phoneTicFirst = phoneTicFirst;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}
