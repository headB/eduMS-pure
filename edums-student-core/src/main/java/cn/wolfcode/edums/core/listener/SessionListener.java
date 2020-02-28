package cn.wolfcode.edums.core.listener;

import cn.wolfcode.edums.core.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;

/**
 * 会话监听器
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SessionListener implements org.apache.shiro.session.SessionListener {
    private Logger logger = LogManager.getLogger();

    /* (non-Javadoc)
     * @see org.apache.shiro.session.SessionListener#onStart(org.apache.shiro.session.Session) */
    @Override
    public void onStart(Session session) {
        session.setAttribute(Constants.WEBTHEME, "default");
        logger.info("创建了一个Session连接:[" + session.getId() + "]");
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.session.SessionListener#onStop(org.apache.shiro.session.Session) */
    @Override
    public void onStop(Session session) {
        session.removeAttribute(Constants.CURRENT_USER);
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.session.SessionListener#onExpiration(org.apache.shiro.session.Session) */
    @Override
    public void onExpiration(Session session) {
        onStop(session);
    }
}
