package life.usc.study.service.admin;

import life.usc.study.mapper.AdminMapper;
import life.usc.study.model.Admin;
import life.usc.study.model.AdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminLoginService {
    @Autowired
    AdminMapper adminMapper;

    public Admin login(String username, String password) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().
                andNameEqualTo(username).
                andPasswordEqualTo(password);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        return  admins.get(0);
    }
}
