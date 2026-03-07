package vnua.fita.tthieu.springboot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    
    // Người tạo
    private String createdBy;

    // Người sửa gần nhất
    private String updatedBy;

	// Constructors ko tham số
    public Project() {}
    
    // Constructor có tham số chỉ cần nếu Dev cần dùng riêng, JPA ko cần
    // Tuy nhiên JPA cần constructor ko tham số để gọi tạo đối tượng
    // Nếu đã định nghĩa constructor có tham số thì Java ko cấp constructor ko tham 
    // số mặc định nữa do đó phải định nghĩa constructor không tham số đi kèm (như ở trên), 
    // nếu ko JPA sẽ ko hoạt động tạo đối tượng đc
    // Nếu Dev ko cần dùng riêng, ko cần định nghĩa constructor nào hết, dùng mặc định của Java
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
    
}

