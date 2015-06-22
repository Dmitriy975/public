package com.project.dao;

import com.project.model.Menu;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuDaoImpl implements MenuDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addMenu(Menu menu) {
       this.sessionFactory.getCurrentSession().save(menu);
    }

    public void updateMenu(Menu menu) {
        this.sessionFactory.getCurrentSession().update(menu);
    }

    /**
     * Получить список всех меню в виде дерева
     * @return List<Menu>
     */
    @SuppressWarnings("unchecked")
    public List<Menu> listMenus() {
        return this.sessionFactory.getCurrentSession().createQuery("from Menu where menu_id is null order by order_index ASC").list();
    }

    @Override
    public Menu getMenuById(int id) {
        //todo гдето нашёл покомпактнее. Не проверял работает ли. Вариант в комментарии точно работает
        return (Menu) this.sessionFactory.getCurrentSession().get(Menu.class, id);
        //return (Menu) this.sessionFactory.getCurrentSession().
        //        createQuery("from Menu where id = ?").setInteger(0, id).uniqueResult();
    }

    /**
     * Запрос для изменения порядка сортировки работает по принципу:
     * есть у нас элементы 1:а 2:б 3:в 4:г 5:д 6:е перемещаем элемент "б" между "д" и "е", для этого смещаем элементы
     * начиная со 2-го(orderBefore) и заканчивая 5ым(orderAfter) на 1ин влево (iteration = -1) получем 1:а,2:д,3:б,4:в,5:г,6:е
     * если требуется переместить "д" между "а" и "б" тоже самое только двигаем вправо (iteration = 1)
     */
    private final static String CHANGE_ORDER =
            "UPDATE Menu " +
            "SET order_index = order_index + :order " +
            "WHERE order_index >= :orderBefore " +
                    "AND ((:parrentId is null and menu_id is null) or menu_id = :parrentId)";

    @Override
    public void changeOrder(Integer parentId, int indexOrderBefore, Integer indexOrderAfter, int iteration) {
        String queryString = CHANGE_ORDER;
        if (indexOrderAfter != null) {
            queryString += "AND order <= :orderAfter ";
        }
        Query query = this.sessionFactory.getCurrentSession().createQuery(queryString);
        query.setParameter("parrentId", parentId, new IntegerType());
        query.setParameter("orderBefore", indexOrderBefore);
        if (indexOrderAfter != null) {
            query.setParameter("orderAfter", indexOrderAfter);
        }
        query.setParameter("order", iteration);
        query.executeUpdate();
    }

}
