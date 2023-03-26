package com.yj.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yj.edu.entity.Subject;
import com.yj.edu.entity.Teacher;
import com.yj.edu.entity.subject.SubjectData;
import com.yj.edu.mapper.SubjectMapper;
import com.yj.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author yujun
 * @since 2023-03-06
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void addSubject(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class, new AnalysisEventListener<SubjectData>() {
                @Override
                public void invoke(SubjectData data, AnalysisContext analysisContext) {
                    saveSubject(data);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {}
            }).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, Object>> queryList() {
        List<Subject> oneSubjects = this.list(new QueryWrapper<Subject>().eq("parent_id", 0));
        List<Subject> twoSubjects = this.list(new QueryWrapper<Subject>().ne("parent_id", 0));
        List<Map<String, Object>> list = new ArrayList<>();
        for(int i=0,len=oneSubjects.size();i<len;i++){
            HashMap<String, Object> map = new HashMap<>();
            Subject subject = oneSubjects.get(i);
            map.put("id",subject.getId());
            map.put("title",subject.getTitle());
            List<Map<String, Object>> children = new ArrayList<>();
            for(int j=0,len2=twoSubjects.size();j<len2;j++){
                Subject child = twoSubjects.get(j);
                if(child.getParentId().equals(subject.getId())){
                    HashMap<String, Object> childMap = new HashMap<>();
                    childMap.put("id",child.getId());
                    childMap.put("title",child.getTitle());
                    children.add(childMap);
                    map.put("children",children);
                }
            }
            list.add(map);
        }
        return list;
    }

    public void saveSubject(SubjectData data){
        if(data == null){
            return;
        }
        String oneSubject = data.getOneSubject();
        String twoSubject = data.getTwoSubject();
        if(!StringUtils.isEmpty(oneSubject)&&!StringUtils.isEmpty(twoSubject)){
            Subject one = this.getOne(new QueryWrapper<Subject>().eq("title", oneSubject).eq("parent_id", "0"));
            if(one == null){
                one = new Subject();
                one.setTitle(oneSubject);
                this.save(one);
            }
            Subject two = this.getOne(new QueryWrapper<Subject>().eq("title", twoSubject).eq("parent_id", one.getId()));
            if(two == null){
                two = new Subject();
                two.setTitle(twoSubject);
                two.setParentId(one.getId());
                this.save(two);
            }
        }
    }
}
