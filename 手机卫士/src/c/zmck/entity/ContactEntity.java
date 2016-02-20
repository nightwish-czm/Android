package c.zmck.entity;

public class ContactEntity {

	public String name;
	
	public String num;
	
	public int id;
	
	@Override
	public String toString() {
		System.out.println(name+num+id);
		return null;
	}
}
