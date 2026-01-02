package vnua.fita.tthieu.springboot.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import vnua.fita.tthieu.springboot.entity.Project;

// Spring tự động scan các class kế thừa từ JpaRepository
// Đưa ra cơ chế tạo bean ProjectRepository trong ApplicationContext ở runtime (thời điểm chạy khi cần đến)
// để tiêm vào nơi cần sử dụng như trong ProjectService: 
//              @Autowired 
//              private ProjectRepository projectRepository;
// Sau khi bean được tạo ra nó được giữ lại để tái sử dụng
@SuppressWarnings("unused")
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
