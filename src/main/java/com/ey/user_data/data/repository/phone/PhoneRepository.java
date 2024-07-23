package com.ey.user_data.data.repository.phone;

import com.ey.user_data.data.entity.phone.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
