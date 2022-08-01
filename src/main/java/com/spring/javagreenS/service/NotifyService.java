package com.spring.javagreenS.service;

import java.util.List;

import com.spring.javagreenS.vo.NotifyVO;

public interface NotifyService {

	public void nInput(NotifyVO vo);

	public List<NotifyVO> getNotifyList();

	public void setDelete(int idx);

	public NotifyVO getNUpdate(int idx);

	public void setNUpdateOk(NotifyVO vo);

	public int setpopupCheckUpdate(int idx, String popupSw);

	public List<NotifyVO> getNotifyPopup();

}
