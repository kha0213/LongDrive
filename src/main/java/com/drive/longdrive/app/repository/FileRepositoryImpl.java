package com.drive.longdrive.app.repository;

import com.drive.longdrive.app.dto.FileSearch;
import com.drive.longdrive.app.dto.entity.File;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import static com.drive.longdrive.app.dto.entity.QFile.file;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<File> findFiles(FileSearch search, Pageable pageable) {
        JPAQuery<File> query = queryFactory
                .selectFrom(file)
                .where(
                        allFieldFileSearch(search)
                )
                .orderBy(file.createBy.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return PageableExecutionUtils.getPage(query.fetch(), pageable, () -> this.totalCount(search) );
    }
    
    private int totalCount(FileSearch search){
        return queryFactory
                .select(
                        file.count())
                .from(file)
                .where(
                        allFieldFileSearch(search)
                )
                .fetch().size();
    }

    private BooleanBuilder allFieldFileSearch(FileSearch search) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(search.getFileName())) {
            builder.and(file.originNm.contains(search.getFileName()));
        }
        if (isPositive(search.getMinSize())) {
            builder.and(file.size.goe(search.getMinSize()));
        }
        if (isPositive(search.getMaxSize())) {
            builder.and(file.size.loe(search.getMaxSize()));
        }
        if (search.getFileType() != null) {
            builder.and(file.fileType.eq(search.getFileType()));
        }
        if (hasText(search.getExtension())) {
            builder.and(file.extensions.eq(search.getExtension()));
        }
        if (search.getMinDate() != null) {
            builder.and(file.createdDate.after(search.getMinDate()));
        }
        if (search.getMaxDate() != null) {
            builder.and(file.createdDate.before(search.getMaxDate()));
        }
        return builder;
    }

    private boolean isPositive(Long num) {
        return num != null && num > 0;
    }
}
