package com.bhge.filestats;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStatsRepository extends CrudRepository<FileStats, Long>  {

}
