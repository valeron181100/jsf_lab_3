package database.dao;

import database.dao.models.ResultPoint;

import java.util.List;

public interface ResultPointDAOable {
    ResultPoint insertPoint(ResultPoint point);

    ResultPoint findResultPoint(String id);

    List<ResultPoint> findAllPoints();

    void removePoint(String id);
}
