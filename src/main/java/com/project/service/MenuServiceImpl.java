package com.project.service;

import com.project.dao.MenuDaoImpl;
import com.project.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("MenuServiceImpl")
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDaoImpl menuDao;

    @Transactional(readOnly = false)
    public void addMenu(Menu menu) {
        this.menuDao.addMenu(menu);
    }

    @Transactional
    public List<Menu> listMenus() {
        return this.menuDao.listMenus();
    }

    @Override
    @Transactional(readOnly = false)
    public void updateMenu(Menu menu) {
        menuDao.updateMenu(menu);
    }

    /**
     * Изменение порядка вывода элементов меню, и изменяем родителя меню
     * Устанавливаем значение order = indexOrder
     * @param menuParent родительский элемент
     * @param menu первое меню
     * @param indexOrderAfter порядковый номер элемента т.е. каким по счёту идёт это меню
     */
    @Override
    @Transactional(readOnly = false)
    public void changeOrderAndParent(Menu menuParent, Menu menu, int indexOrderAfter) {
        int indexOrderBefore = menu.getOrder();
        menu.setOrder(indexOrderAfter);
        // если мы изменили родителя
        if (!menuParent.getChildMenus().contains(menu)) {
            // получаем родителя который был, но родителя может и не быть вообще (shop_id = null)
            Integer oldParentId = menu.getParentMenu() != null ? menu.getParentMenu().getId() : null;
            // у старого родителя смещаем индекс сортировки на -1 начиная с indexOrderBefore (там где раньше находился элемент)
            menuDao.changeOrder(oldParentId, indexOrderBefore, null, -1);
            // у нового родителя смещаем индекс сортировки на +1 начиная с indexOrderAfter
            // (мы вставили элемент на это место, значит двигаем все остальные)
            menuDao.changeOrder(menuParent.getId(), indexOrderAfter, null, 1);
            menu.setParentMenu(menuParent.getId() != null ? menuParent : null);
        } else {
            // Пример: есть у нас элементы 1:а,2:б,3:в,4:г,5:д,6:е перемещаем элемент б на место д, для этого смещаем элементы
            // начиная со 2-го(indexOrderBefore) и заканчивая 5ым(indexOrderAfter) на 1ин влево получем 1:а,2:д,3:б,4:в,5:г,6:е
            if (indexOrderBefore < indexOrderAfter) {
                menuDao.changeOrder(menuParent.getId(), indexOrderBefore, indexOrderAfter, -1);
            } else { //тоже самое только в другую сторону
                menuDao.changeOrder(menuParent.getId(), indexOrderAfter, indexOrderBefore, 1);
            }
        }
        menuDao.updateMenu(menu);


    }

}
