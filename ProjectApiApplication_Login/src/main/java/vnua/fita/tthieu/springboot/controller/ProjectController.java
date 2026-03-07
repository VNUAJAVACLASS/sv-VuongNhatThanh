package vnua.fita.tthieu.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vnua.fita.tthieu.springboot.entity.Project;
import vnua.fita.tthieu.springboot.service.ProjectService;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    // Không sử dụng trực tiếp bean ProjectRepository nếu ngoài việc truy suất CSDL thuần túy
	// còn cần thêm một số xử lý logic -> nên dùng bean Service để code phân tách rõ ràng hơn
	@Autowired
    private ProjectService projectService;

    // Thông tin ds project được public
    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.findAll();
    }
    
    // Thông tin chi tiết project được public
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.findById(id);
    }
    
    // Kiểu dữ liệu trả về (Project) không sử dụng ResponseEntity có nghĩa chỉ trả lại phần response body
    // Các phần response header, status để SpringBoot tự xử lý dựa vào ngữ cảnh
    // role user thường chỉ được quyền tạo, không được quyền sửa, xóa
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Project createProject(@RequestBody Project project) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        project.setCreatedBy(username);
        return projectService.save(project);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Project updateProject(@PathVariable Long id, @RequestBody Project updatedProject) {
        // Ghi vào CSDL: account admin nào thực hiện update
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return projectService.update(id, updatedProject, username);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProject(@PathVariable Long id) {
        projectService.delete(id);
    }
}
