package c.zmck.entity;

public class VersionEntity {

	private String versionName;
	
	private int versionCode;
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public VersionEntity() {
		super();
	}

	public VersionEntity(String versionName, int versionCode, String message) {
		super();
		this.versionName = versionName;
		this.versionCode = versionCode;
		this.message = message;
	}
	
	
	
}
