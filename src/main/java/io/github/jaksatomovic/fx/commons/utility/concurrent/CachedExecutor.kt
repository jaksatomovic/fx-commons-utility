/*
 * Boomega
 * Copyright (C)  2021  Daniel Gyoerffy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.jaksatomovic.fx.commons.utility.concurrent

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton

/**
 * A global cached thread pool for the application.
 *
 * @author Daniel Gyorffy
 */
@Singleton
@Named("cachedExecutor")
object CachedExecutor : ExecutorService
by Executors.newCachedThreadPool({ runnable ->
    Thread(runnable).apply {
        isDaemon = true
    }
})