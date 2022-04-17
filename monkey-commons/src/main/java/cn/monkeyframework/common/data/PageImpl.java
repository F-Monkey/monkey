package cn.monkeyframework.common.data;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageImpl<T> implements org.springframework.data.domain.Page<T> {

    private long total;

    private Pageable pageable;

    private List<T> content;

    public PageImpl() {
        this.content = new ArrayList<>();
    }

    public int getNumber() {
        return this.pageable.isPaged() ? this.pageable.getPageNumber() : 0;
    }

    public int getSize() {
        return this.pageable.isPaged() ? this.pageable.getPageSize() : this.content.size();
    }

    public int getNumberOfElements() {
        return this.content.size();
    }

    public boolean hasPrevious() {
        return this.getNumber() > 0;
    }

    public boolean isFirst() {
        return !this.hasPrevious();
    }

    public boolean isLast() {
        return !this.hasNext();
    }

    public @NonNull
    Pageable nextPageable() {
        return this.hasNext() ? this.pageable.next() : Pageable.unpaged();
    }

    public @NonNull
    Pageable previousPageable() {
        return this.hasPrevious() ? this.pageable.previousOrFirst() : Pageable.unpaged();
    }

    public boolean hasContent() {
        return !this.content.isEmpty();
    }

    public @NonNull
    List<T> getContent() {
        return Collections.unmodifiableList(this.content);
    }

    public @NonNull
    Pageable getPageable() {
        return this.pageable;
    }

    public @NonNull
    Sort getSort() {
        return this.pageable.getSort();
    }

    public @NonNull
    Iterator<T> iterator() {
        return this.content.iterator();
    }

    public int getTotalPages() {
        return this.getSize() == 0 ? 1 : (int) Math.ceil((double) this.total / (double) this.getSize());
    }

    public long getTotalElements() {
        return this.total;
    }

    public boolean hasNext() {
        return this.getNumber() + 1 < this.getTotalPages();
    }

    public @NonNull <U> Page<U> map(@NonNull Function<? super T, ? extends U> converter) {
        PageImpl<U> page = new PageImpl<>();
        page.setTotal(this.total);
        page.setPageable(this.getPageable());
        page.setContent(this.getConvertedContent(converter));
        return page;
    }

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        Assert.notNull(converter, "Function must not be null!");
        return this.stream().map(converter).collect(Collectors.toList());
    }

    public String toString() {
        String contentType = "UNKNOWN";
        List<T> content = this.getContent();
        if (!content.isEmpty() && content.get(0) != null) {
            contentType = content.get(0).getClass().getName();
        }

        return String.format("Page %s of %d containing %s instances", this.getNumber() + 1, this.getTotalPages(), contentType);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        } else if (!(obj instanceof PageImpl)) {
            return false;
        } else {
            PageImpl<?> that = (PageImpl) obj;
            return this.total == that.total && super.equals(obj);
        }
    }

    public int hashCode() {
        int result = 17;
        result += 31 * (int) (total ^ total >>> 32);
        result += 31 * super.hashCode();
        return result;
    }
}
