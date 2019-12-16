package com.xpjava.medicine.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xpjava.medicine.entity.MedicineChn;
import com.xpjava.medicine.entity.MedicineEn;
import com.xpjava.medicine.service.MedicineChnService;
import com.xpjava.medicine.service.MedicineEnService;
import com.xpjava.medicine.util.CookieUtil;
import com.xpjava.medicine.util.JwtUtil;
import com.xpjava.medicine.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @since 2019-12-03
 */
@Controller
@RequestMapping("/medicine")
public class MedicineController {

    @Autowired
    MedicineChnService medicineChnService;

    @Autowired
    MedicineEnService medicineEnService;

    @GetMapping("/list/chn")
    public String medicineChinaList(ModelMap map,HttpServletRequest request, Search search){
        String token = CookieUtil.getCookieValue(request, "userInfo", true);
        String userName = CookieUtil.getCookieValue(request, "userName", true);
        String role = (String) JwtUtil.decode(token, "xpjava", "xpjava").get("role");
        QueryWrapper<MedicineChn> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if(search!=null){
            if(!StringUtils.isEmpty(search.getName())){
                queryWrapper.like("goods_name",search.getName());
            }
            if(!StringUtils.isEmpty(search.getProduceDateBegin())){
                queryWrapper.ge("produce_date",search.getProduceDateBegin());
            }
            if(!StringUtils.isEmpty(search.getProduceDateEnd())){
                queryWrapper.le("produce_date",search.getProduceDateEnd());
            }
            if(!StringUtils.isEmpty(search.getExpireDateBegin())){
                queryWrapper.ge("expire_date",search.getExpireDateBegin());
            }
            if(!StringUtils.isEmpty(search.getExpireDateEnd())){
                queryWrapper.le("expire_date",search.getExpireDateEnd());
            }
        }
        List<MedicineChn> medicineChnList = medicineChnService.list(queryWrapper);
        map.put("medicineChnList",medicineChnList);
        map.put("search",search);
        map.put("userName",userName);
        if (role.equals("1")) {
            return "medicine-normal-chn-list";
        } else {
            return "medicine-chn-list";
        }
    }

    @GetMapping("/list/en")
    public String medicineEnList(ModelMap map, HttpServletRequest request, Search search){
        String token = CookieUtil.getCookieValue(request, "userInfo", true);
        String userName = CookieUtil.getCookieValue(request, "userName", true);
        String role = (String) JwtUtil.decode(token, "xpjava", "xpjava").get("role");
        QueryWrapper<MedicineEn> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        if(search!=null){
            if(!StringUtils.isEmpty(search.getName())){
                queryWrapper.like("goods_name",search.getName());
            }
            if(!StringUtils.isEmpty(search.getProduceDateBegin())){
                queryWrapper.ge("produce_date",search.getProduceDateBegin());
            }
            if(!StringUtils.isEmpty(search.getProduceDateEnd())){
                queryWrapper.le("produce_date",search.getProduceDateEnd());
            }
            if(!StringUtils.isEmpty(search.getExpireDateBegin())){
                queryWrapper.ge("expire_date",search.getExpireDateBegin());
            }
            if(!StringUtils.isEmpty(search.getExpireDateEnd())){
                queryWrapper.le("expire_date",search.getExpireDateEnd());
            }
        }
        List<MedicineEn> medicineEnList = medicineEnService.list(queryWrapper);
        map.put("medicineEnList",medicineEnList);
        map.put("search",search);
        map.put("userName",userName);
        if (role.equals("1")) {
            return "medicine-normal-en-list";
        } else {
            return "medicine-en-list";
        }
    }

    @GetMapping("/chn/{id}")
    public String updateMedicineChn(@PathVariable("id")String id,ModelMap map,HttpServletRequest request){
        MedicineChn medicineChn = medicineChnService.getById(id);
        String userName = CookieUtil.getCookieValue(request, "userName", true);
        medicineChn.setProduceDateTemp(String.valueOf(medicineChn.getProduceDate()));
        medicineChn.setExpireDateTemp(String.valueOf(medicineChn.getExpireDate()));
        map.put("medicineChn",medicineChn);
        map.put("userName",userName);
        return "medicine-chn-edit";
    }

    @GetMapping("/chn")
    public String toMedicineChnAddPage(ModelMap map,HttpServletRequest request){
        String userName = CookieUtil.getCookieValue(request, "userName", true);
        map.put("userName",userName);
        return "medicine-chn-edit";
    }

    @PostMapping("/chn/{id}")
    public String deleteMedicineChn(@PathVariable("id")Integer id, RedirectAttributes map){
        medicineChnService.removeById(id);
        map.addFlashAttribute("successMsg","删除成功");
        return "redirect:/medicine/list/chn";
    }

    @GetMapping("/en/{id}")
    public String updateMedicineEn(@PathVariable("id")String id,ModelMap map,HttpServletRequest request){
        MedicineEn medicineEn = medicineEnService.getById(id);
        String userName = CookieUtil.getCookieValue(request, "userName", true);
        medicineEn.setProduceDateTemp(String.valueOf(medicineEn.getProduceDate()));
        medicineEn.setExpireDateTemp(String.valueOf(medicineEn.getExpireDate()));
        map.put("medicineEn",medicineEn);
        map.put("userName",userName);
        return "medicine-en-edit";
    }

    @GetMapping("/en")
    public String toMedicineEnAddPage(ModelMap map,HttpServletRequest request){
        String userName = CookieUtil.getCookieValue(request, "userName", true);
        map.put("userName",userName);
        return "medicine-en-edit";
    }

    @PostMapping("/en/{id}")
    public String deleteMedicineEn(@PathVariable("id")Integer id, RedirectAttributes map){
        medicineEnService.removeById(id);
        map.addFlashAttribute("successMsg","删除成功");
        return "redirect:/medicine/list/en";
    }

    @PostMapping("/chn/save")
    public String saveMedicineChn(MedicineChn medicineChn, RedirectAttributes map) throws ParseException {
        String expireDateTemp = medicineChn.getExpireDateTemp();
        String produceDateTemp = medicineChn.getProduceDateTemp();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        medicineChn.setProduceDate(sdf.parse(expireDateTemp));
        medicineChn.setExpireDate(sdf.parse(produceDateTemp));
        medicineChnService.saveOrUpdate(medicineChn);
        map.addFlashAttribute("successMsg","操作成功");
        return "redirect:/medicine/list/chn";
    }

    @PostMapping("/en/save")
    public String saveMedicineEn(MedicineEn medicineEn, RedirectAttributes map) throws ParseException {
        String expireDateTemp = medicineEn.getExpireDateTemp();
        String produceDateTemp = medicineEn.getProduceDateTemp();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        medicineEn.setProduceDate(sdf.parse(expireDateTemp));
        medicineEn.setExpireDate(sdf.parse(produceDateTemp));
        medicineEnService.saveOrUpdate(medicineEn);
        map.addFlashAttribute("successMsg","操作成功");
        return "redirect:/medicine/list/en";
    }

}

