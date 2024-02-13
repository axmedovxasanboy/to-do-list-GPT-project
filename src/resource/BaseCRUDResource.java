package resource;

import bean.ApiResponse;
import bean.BaseBean;

public interface BaseCRUDResource<T extends BaseBean> {
    ApiResponse create();

    ApiResponse view();

    ApiResponse update(Integer id);

    ApiResponse delete(Integer id);
}
