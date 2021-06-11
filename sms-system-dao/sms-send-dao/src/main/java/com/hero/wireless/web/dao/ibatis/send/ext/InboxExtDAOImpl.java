package com.hero.wireless.web.dao.ibatis.send.ext;

import com.hero.wireless.web.dao.ibatis.MybatisBaseSendExtDao;
import com.hero.wireless.web.dao.send.ext.IInboxExtDAO;
import com.hero.wireless.web.entity.send.Inbox;
import com.hero.wireless.web.entity.send.InboxExample;
import com.hero.wireless.web.entity.send.ext.InboxExt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("inboxExtDAO")
public class InboxExtDAOImpl extends MybatisBaseSendExtDao<InboxExt, InboxExample, Inbox> implements IInboxExtDAO {


    @Override
    public void updateInboxList(List<Inbox> inboxList) {
        getSqlSendSessionTemplate().update("updateInboxList",inboxList);
    }

    @Override
    public List<Map<String, Object>> queryInboxListForExportPage(InboxExample example) {
        return getSqlSendSessionTemplate().selectList("queryInboxListForExportPage", example);
    }

}
