package org.hbhk.aili.orm.server;

import org.hbhk.aili.orm.server.annotation.Column;
import org.hbhk.aili.orm.server.annotation.Id;
import org.hbhk.aili.orm.server.annotation.Tabel;
import org.hbhk.aili.orm.share.model.BaseInfo;

@Tabel("t_test_sql")
public class SqlModelTest extends BaseInfo {

	private static final long serialVersionUID = 4797694839375226216L;
	@Column("id")
	@Id
	private String id;
	@Column("test_sss")
	private String sss;
	@Column("test_qqq")
	private String qqq;

	public String getSss() {
		return sss;
	}

	public void setSss(String sss) {
		this.sss = sss;
	}

	public String getQqq() {
		return qqq;
	}

	public void setQqq(String qqq) {
		this.qqq = qqq;
	}


}
