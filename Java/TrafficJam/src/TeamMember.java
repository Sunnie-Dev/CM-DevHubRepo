
/// Casey Munga
public class TeamMember {
	String Name;
	Integer Pos;
	String Color;
	
	
	TeamMember (){
		
	}
	TeamMember(String name, Integer pos, String color)  {
		
	this.Name=name;
	this.Pos=pos;
	this.Color=color;
	
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getPos() {
		return Pos;
	}

	public void setPos(Integer pos) {
		Pos = pos;
	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	
}
