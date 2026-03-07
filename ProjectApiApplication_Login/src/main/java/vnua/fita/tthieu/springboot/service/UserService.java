package vnua.fita.tthieu.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vnua.fita.tthieu.springboot.entity.User;
import vnua.fita.tthieu.springboot.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	/**
	 * Lấy user theo ID
	 */
	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
	}
}
