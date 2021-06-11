package com.hero.wireless.web.dao.send.ext;

import com.hero.wireless.web.dao.ext.IExtDAO;
import com.hero.wireless.web.dao.send.IInboxDAO;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.InboxExample;
import com.hero.wireless.web.entity.send.ext.InboxExt;

import java.util.List;
import java.util.Map;

public interface IInboxExtDAO extends IInboxDAO<InboxExt>,IExtDAO<InboxExt, InboxExample> {

    void updateInboxList(List<Inbox> inboxList);

    List<Map<String, Object>> queryInboxListForExportPage(InboxExample example);
}