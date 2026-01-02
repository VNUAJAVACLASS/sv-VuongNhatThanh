package vnua.fita.tthieu.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vnua.fita.tthieu.springboot.entity.Project;
import vnua.fita.tthieu.springboot.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Lấy tất cả project (public)
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    // Lấy theo ID (nếu cần)
    // Trong SpringBoot có cơ chế xử lý ngoại lệ toàn cục, cá exception đẩy ra ở RestController
    // sẽ được bắt xử lý chuyển đổi thành Http response trả lại Client mà không đẩy ra JVM như 
    // chương trình Java thuần làm ngắt chương trình.
    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project không tồn tại"));
    }

    // Tạo mới project
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    // Cập nhật (chỉ admin)
    public Project update(Long id, Project updatedProject, String updatedBy) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project không tồn tại"));

        existing.setName(updatedProject.getName());
        existing.setDescription(updatedProject.getDescription());
        existing.setUpdatedBy(updatedBy); // account admin nào thực hiện update

        return projectRepository.save(existing);
    }

    // Xóa (chỉ admin)
    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project không tồn tại");
        }
        projectRepository.deleteById(id);
    }
}
