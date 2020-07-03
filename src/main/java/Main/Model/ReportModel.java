package Main.Model;

public class ReportModel {

	private int id;
	private String user_name;
	private String report_body;
	private String report_area;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getReport_body() {
		return report_body;
	}
	public void setReport_body(String report_body) {
		this.report_body = report_body;
	}
	public String getReport_area() {
		return report_area;
	}
	public void setReport_area(String report_area) {
		this.report_area = report_area;
	}
	
	public ReportModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ReportModel [id=" + id + ", user_name=" + user_name + ", report_body=" + report_body + ", report_area="
				+ report_area + "]";
	}
	
	
}
