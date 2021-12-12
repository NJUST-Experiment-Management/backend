package com.experiment.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.experiment.common.Result;
import com.experiment.entity.*;
import com.experiment.mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArrangeServiceImpl implements ArrangeService{

    @Resource
    ArrRoomMapper arrRoomMapper;
    @Resource
    ArrUserMapper arrUserMapper;
    @Resource
    ArrCourseMapper arrCourseMapper;
    @Resource
    RoomMapper roomMapper;
    @Resource
    CourseTableMapper courseTableMapper;
    @Resource
    CourseService courseService;
    @Resource
    DeviceService deviceService;
    @Resource
    CourseMapper courseMapper;

    @Override
    public Result<?> addArrangement(Course course, Date date, Integer time, List<Room> roomList, Boolean isShareable) {
        QueryWrapper<ArrRoom> queryWrapper = new QueryWrapper<ArrRoom>()
                .eq("arrange_date",date)
                .eq("arrange_time",time);
        List<ArrRoom> arrRooms = arrRoomMapper.selectList(queryWrapper);
        for(ArrRoom arrRoom : arrRooms){
            if(!arrRoom.isShareable() || !isShareable){
                return Result.error("-1", "您所排课的教室已被占用，请刷新页面后重试");
            }
        }
        List<String> studentList = (List<String>) courseService.getStudentList(course).getData();
        List<Device> deviceList = (List<Device>) deviceService.getAvailableDevice(roomList, date, time).getData();
        if(studentList.size() > deviceList.size()){
            return Result.error("-1", "您选择房间的机位数不足以放下您的学生");
        }
        for(Room room : roomList) {
            arrRoomMapper.insert(new ArrRoom(IdUtil.fastSimpleUUID(), room.getRoomId(), course.getCourseId(), date, time, isShareable));  //插入房间安排
        }
        for(int i = 0; i < studentList.size(); i++){
            arrUserMapper.insert(new ArrUser(IdUtil.fastSimpleUUID(), studentList.get(i),
                    date, time, course.getCourseId(), deviceList.get(i).getDeviceId()));
        }
        return Result.success();
    }

    @Override
    public Result<?> getAllArrangement() {
        List<CourseTable> courseTables = courseTableMapper.selectList(new QueryWrapper<CourseTable>());
        return Result.success(courseTables);
    }

    @Override
    public Result<?> getArrangementByRoomId(String roomId) {
        List<ArrCourse> arrCourses = arrCourseMapper.selectList(new QueryWrapper<ArrCourse>()
                .eq("room_id", roomId));
        return Result.success(arrCourses);
    }

    @Override
    public Result<?> getStudentArrangement(String studentId) {
        List<CourseTable> courseTableList = courseTableMapper.selectList(new QueryWrapper<CourseTable>()
                .eq("user_id", studentId));
        return Result.success(courseTableList);
    }

    @Override
    public Result<?> getTeacherArrangement(String teacherId) {
        List<Course> courseList = courseMapper.selectList(new QueryWrapper<Course>()
                .eq("teacher_id", teacherId));
        List<String> courseIds = new ArrayList<>();
        for(Course course : courseList)
            courseIds.add(course.getCourseId());
        List<ArrCourse> arrCourses = arrCourseMapper.selectList(new QueryWrapper<ArrCourse>()
                .in("course_id", courseIds));
        return Result.success(arrCourses);
    }

    @Override
    public Result<?> quitOpenCourse(User student, Date date, Integer time) {
        ArrUser arrUser = arrUserMapper.selectOne(new QueryWrapper<ArrUser>()
                .eq("user_id", student.getUserId())
                .eq("arrange_date", date)
                .eq("arrange_time", time));
        if(arrUser == null){
            return Result.error("-1", "系统错误");
        }
        arrUserMapper.deleteById(arrUser.getArrUserId());
        ArrUser tempArrUser = arrUserMapper.selectOne(new QueryWrapper<ArrUser>().eq("course_id", arrUser.getCourseId()).eq("arrange_date", date).eq("arrange_time", time));
        if(tempArrUser == null){
            arrRoomMapper.delete(new QueryWrapper<ArrRoom>()
                    .eq("course_id", arrUser.getCourseId())
                    .eq("arrange_time", arrUser.getArrangeTime())
                    .eq("arrange_date", arrUser.getArrangeDate()));
        }
        return Result.success();
    }

    @Override
    public Result<?> getArrangementByTimeRoom(Date date, Integer time, String roomId) {
        List<ArrCourse> arrCourses = arrCourseMapper.selectList(new QueryWrapper<ArrCourse>()
                .eq("arrange_time", time)
                .eq("arrange_date", date)
                .eq("room_id", roomId));
        return Result.success(arrCourses);
    }

    @Override
    public Result<?> getArrangementByTime(List<Date> dateList) {
        List<CourseTable> courseTableList = courseTableMapper.selectList(new QueryWrapper<CourseTable>()
                .in("arrange_date", dateList)
                .orderByAsc("arrange_date", "arrange_time"));
        return Result.success(courseTableList);
    }

    @Override
    public Result<?> getArrangementByTime(Date date, Integer time) {
        List<Room> rooms = roomMapper.selectList(new QueryWrapper<Room>()
                .orderByAsc("room_id"));
        List<List<ArrCourse> > courseList = new ArrayList<>();
        for(int i = 0; i < rooms.size(); i++){
            List<ArrCourse> arrCourses = arrCourseMapper.selectList(new QueryWrapper<ArrCourse>()
                    .eq("arrange_date", date)
                    .eq("arrange_time", time)
                    .eq("room_id", rooms.get(i).getRoomId()));
            courseList.add(arrCourses);
        }
        Object[] objects = {rooms, courseList};
        return Result.success(objects);
    }

    @Override
    public Result<?> chooseOpenCourse(User student, Course course, Room room, Date date, Integer time) {
        QueryWrapper<ArrRoom> queryWrapper = new QueryWrapper<ArrRoom>()
                .eq("arrange_date",date)
                .eq("arrange_time",time);
        List<ArrRoom> arrRooms = arrRoomMapper.selectList(queryWrapper);
        boolean isHasOccupied = false;
        for(ArrRoom arrRoom : arrRooms){
            if(!arrRoom.isShareable()){
                return Result.error("-1", "您所选择的教室已被占用，请刷新页面后重试");
            }
            if(arrRoom.getCourseId().equals(course.getCourseId()))
                isHasOccupied = true;
        }
        List<Room> tmpRoom = new ArrayList<>();
        tmpRoom.add(room);
        List<Device> deviceList = (List<Device>) deviceService.getAvailableDevice(tmpRoom, date, time);
        if(deviceList.size() < 3){
            return Result.error("-1", "您所选择房间的机位数不足，请刷新页面后重试");
        }
        if(!isHasOccupied){
            arrRoomMapper.insert(new ArrRoom(IdUtil.fastSimpleUUID(), room.getRoomId(), course.getCourseId(), date, time, true));
        }
        arrUserMapper.insert(new ArrUser(IdUtil.fastSimpleUUID(), student.getUserId(), date, time, course.getCourseId(), deviceList.get(0).getDeviceId()));
        return Result.success();
    }
}
