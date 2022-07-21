
package com.spring.javagreenS.service;

import java.util.List;

import com.spring.javagreenS.vo.ScheduleVO;

public interface ScheduleService {

	public void getSchedule();

	public List<ScheduleVO> getScMenu(String mid, String ymd);

	public void scheduleInputOk(ScheduleVO vo);

	public void scheduleDeleteOk(int idx);

	public ScheduleVO getScheduleSearch(int idx);

}
