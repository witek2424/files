package pl.lukasz.gamekiller;

public class Item {
	
	private String value;
	private String randomValue;
	private boolean isModified;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getRandomValue() {
		return randomValue;
	}
	
	public void setRandomValue(String randomValue) {
		this.randomValue = randomValue;
	}
	
	public boolean isModified() {
		return isModified;
	}
	
	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

}
