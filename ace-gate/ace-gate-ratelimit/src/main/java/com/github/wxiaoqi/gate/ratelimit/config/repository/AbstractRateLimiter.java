/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.wxiaoqi.gate.ratelimit.config.repository;

import com.github.wxiaoqi.gate.ratelimit.config.Rate;
import com.github.wxiaoqi.gate.ratelimit.config.RateLimiter;
import com.github.wxiaoqi.gate.ratelimit.config.properties.RateLimitProperties.Policy;

import java.util.Date;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Abstract implementation for {@link RateLimiter}.
 *
 * @author Liel Chayoun
 * @author Marcos Barbero
 * @since 2017-08-28
 */
public abstract class AbstractRateLimiter implements RateLimiter {

    protected abstract Rate getRate(String key);
    protected abstract void saveRate(Rate rate);

    @Override
    public synchronized Rate consume(final Policy policy, final String key) {
        Rate rate = this.create(policy, key);
        this.updateRate(rate);
        this.saveRate(rate);
        return rate;
    }

    private Rate create(final Policy policy, final String key) {
        Rate rate = this.getRate(key);
        if (isExpired(rate)) {

            final Long limit = policy.getLimit();
            final Long refreshInterval = SECONDS.toMillis(policy.getRefreshInterval());
            final Date expiration = new Date(System.currentTimeMillis() + refreshInterval);

            rate = new Rate(key, limit, refreshInterval, expiration);
        }
        return rate;
    }

    private void updateRate(final Rate rate) {
        if (rate.getReset() > 0) {
            Long reset = rate.getExpiration().getTime() - System.currentTimeMillis();
            rate.setReset(reset);
        }
        rate.setRemaining(Math.max(-1, rate.getRemaining() - 1));
    }

    private boolean isExpired(final Rate rate) {
        return rate == null || (rate.getExpiration().getTime() < System.currentTimeMillis());
    }
}
