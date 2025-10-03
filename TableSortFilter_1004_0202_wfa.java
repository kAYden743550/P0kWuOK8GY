// 代码生成时间: 2025-10-04 02:02:28
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * A utility class to perform sorting and filtering on a table using Hibernate.
 */
public class TableSortFilter {
    private Session session;

    /**
     * Constructor to initialize the Hibernate Session.
     * @param session Hibernate session
     */
    public TableSortFilter(Session session) {
        this.session = session;
    }

    /**
     * Performs sorting and filtering on a table with given conditions.
     * @param entityClass The entity class to be sorted and filtered.
     * @param sortField The field to sort by.
     * @param sortOrder The order of sorting (e.g., 