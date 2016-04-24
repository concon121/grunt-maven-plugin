package net.cbr.software.grunt_runner;

public enum GruntFileExtension {
	
	JS (".js"),
	COFFEE (".coffee");
	
	private String ext;
	
	private GruntFileExtension(String ext) {
		this.ext = ext;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}
	
}
