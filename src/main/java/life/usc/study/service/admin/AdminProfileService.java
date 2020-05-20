package life.usc.study.service.admin;

import com.sun.org.apache.xpath.internal.operations.Bool;
import life.usc.study.mapper.AdminMapper;
import life.usc.study.model.Admin;
import life.usc.study.model.AdminExample;
import life.usc.study.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminProfileService {
    @Autowired
    AdminMapper adminMapper;

    public Admin getAdminUser(Integer loginUserId) {
        Admin adminUser = adminMapper.selectByPrimaryKey(loginUserId);
        return adminUser;
    }


    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        Admin adminUser = adminMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            adminUser.setName(loginUserName);
            adminUser.setNickName(nickName);
            if (adminMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                //修改成功则返回true
                return true;
            }
        }
        return false;
    }

    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        Admin adminUser = adminMapper.selectByPrimaryKey(loginUserId);
        if (adminUser != null) {
            String originalPasswordMd5 = MD5Util.MD5Encode(originalPassword, "UTF-8");
            String newPasswordMd5 = MD5Util.MD5Encode(newPassword, "UTF-8");
            if (originalPasswordMd5.equals(adminUser.getPassword())) {
                adminUser.setPassword(newPasswordMd5);
                if (adminMapper.updateByPrimaryKey(adminUser) > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
