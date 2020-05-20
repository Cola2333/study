package life.usc.study.service.admin;

import life.usc.study.mapper.AdminMapper;
import life.usc.study.model.Admin;
import life.usc.study.model.AdminExample;
import life.usc.study.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminLoginService {
    @Autowired
    AdminMapper adminMapper;

    public Admin login(String username, String password) {
        String passwordMd5 = MD5Util.MD5Encode(password, "UTF-8");
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().
                andNameEqualTo(username).
                andPasswordEqualTo(passwordMd5);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        return  admins.isEmpty() ? null : admins.get(0);
    }
}
