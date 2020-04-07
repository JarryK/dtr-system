package cn.edu.mju.ccce.dtrsystem.controller;

import cn.edu.mju.ccce.dtrsystem.bmo.FileBmo;
import cn.edu.mju.ccce.dtrsystem.common.G;
import cn.edu.mju.ccce.dtrsystem.common.MapTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * <b>项目名称：</b>dtr-system<br>
 * <b>类名称：</b>cn.edu.mju.ccce.dtrsystem.controller.FileController<br>
 * <b>创建人：</b>yuks<br>
 * <b>类描述：</b>用户信息导入文件上下传控制<br>
 * <b>创建时间：</b>2020-04-06 13:56<br>
 */
@Controller
@RequestMapping("dtr/user-file")
public class FileController {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Resource(name = "cn.edu.mju.ccce.dtrsystem.bmo.FileBmoImpl")
    private FileBmo fileBmo;

    @RequestMapping("upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()){
            return G.page.returnMap(false, "上传为空！");
        }
        String fileName = file.getOriginalFilename();
        log.info("上传文件名={}",fileName);
//        String filePath = "C:\\dtr-system\\file\\";
//        File f = new File(filePath);
//        if (!f.exists()) {
//            f.mkdirs();// 不存在路径则进行创建
//        }
//        FileOutputStream out = null;
//        try {
//            // 重新自定义文件的名称
//            // 本来打算做成日志流水的，看有没有时间，有时间就做吧
////            filePath = filePath + IdGenerator.getTimestamp() + fileType;
//            filePath = filePath + "check" + fileType;
//            out = new FileOutputStream(filePath);
//            out.write(file.getBytes());
//            out.flush();
//            out.close();
//            log.info("文件上传成功");
//            return G.page.returnMap(true, "ok");
//        } catch (Exception e) {
//            log.error("文件上传异常", e);
//            return G.page.returnMap(false, "文件上传异常");
//        }
        InputStream in = file.getInputStream();
        try {
            Map<String,Object> relMap = fileBmo.parseExcelFileByUser(in,fileName);
            boolean relMapBoolean = G.bmo.returnMapBool(relMap);
            if (!relMapBoolean) {
                String msg = G.bmo.returnMapMsg(relMap);
                Map<String,Object> returnMap = G.page.returnMap(false, msg);
                return returnMap;
            }
            Map<String, Object> returnMap = G.page.returnMap(true,"ok");
            returnMap.put("ExcelStatus", MapTool.getObject(relMap,"ExcelStatus"));
            return returnMap;
        } catch (Exception e) {
            log.error("Excel导入异常",e);
            return G.page.returnMap(false,"Excel导入异常");
        } finally {
            if (null != in) {
                try {
                    // 释放资源
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("download")
    @ResponseBody
    public Map<String, Object> download(@RequestParam("file") MultipartFile file) throws IOException {

        return null;
    }

}
