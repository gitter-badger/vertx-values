package actors.mongo;

import jsonvalues.JsObj;

import java.util.concurrent.TimeUnit;


public class FindMessageBuilder {
    private JsObj filter;
    private JsObj sort;
    private JsObj projection;
    private JsObj hint;
    private JsObj max;
    private JsObj min;
    private String hintString;
    private Integer skip = 0;
    private Integer limit = 0;
    private boolean showRecordId;
    private boolean returnKey;
    private String comment;
    private boolean noCursorTimeout;
    private boolean partial;
    private boolean oplogReplay;
    private Integer batchSize = 100;
    private Long maxAwaitTime = 0L;
    private Long maxTime = 0L;

    public FindMessageBuilder filter(final JsObj filter) {
        this.filter = filter;
        return this;
    }

    public FindMessageBuilder sort(final JsObj sort) {
        this.sort = sort;
        return this;
    }

    public FindMessageBuilder projection(final JsObj projection) {
        this.projection = projection;
        return this;
    }

    public FindMessageBuilder hint(final JsObj hint) {
        this.hint = hint;
        return this;
    }

    public FindMessageBuilder max(final JsObj max) {
        this.max = max;
        return this;
    }

    public FindMessageBuilder min(final JsObj min) {
        this.min = min;
        return this;
    }

    public FindMessageBuilder hintString(final String hintString) {
        this.hintString = hintString;
        return this;
    }

    public FindMessageBuilder skip(final Integer skip) {
        this.skip = skip;
        return this;
    }

    public FindMessageBuilder limit(final Integer limit) {
        this.limit = limit;
        return this;
    }

    public FindMessageBuilder showRecordId(final boolean showRecordId) {
        this.showRecordId = showRecordId;
        return this;
    }

    public FindMessageBuilder returnKey(final boolean returnKey) {
        this.returnKey = returnKey;
        return this;
    }

    public FindMessageBuilder comment(final String comment) {
        this.comment = comment;
        return this;
    }

    public FindMessageBuilder noCursorTimeout(final boolean noCursorTimeout) {
        this.noCursorTimeout = noCursorTimeout;
        return this;
    }

    public FindMessageBuilder partial(final boolean partial) {
        this.partial = partial;
        return this;
    }

    public FindMessageBuilder oplogReplay(final boolean oplogReplay) {
        this.oplogReplay = oplogReplay;
        return this;
    }

    public FindMessageBuilder batchSize(final Integer batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public FindMessageBuilder maxAwaitTime(final int maxAwaitTime,
                                           final TimeUnit unit) {
        this.maxAwaitTime = unit.toMillis(maxAwaitTime);
        return this;
    }

    public FindMessageBuilder maxTime(final int maxTime,
                                      final TimeUnit unit) {
        this.maxTime = unit.toMillis(maxTime);

        return this;
    }

    public FindMessage create() {

        return new FindMessage(filter,
                               sort,
                               projection,
                               hint,
                               max,
                               min,
                               hintString,
                               skip,
                               limit,
                               showRecordId,
                               returnKey,
                               comment,
                               noCursorTimeout,
                               partial,
                               oplogReplay,
                               batchSize,
                               maxAwaitTime,
                               maxTime
        );
    }
}