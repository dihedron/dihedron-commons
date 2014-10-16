/**
 * Copyright (c) 2012-2014, Andrea Funto'. All rights reserved.
 * 
 * This file is part of the Dihedron Common Utilities library ("Commons").
 *
 * "Commons" is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * "Commons" is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with "Commons". If not, see <http://www.gnu.org/licenses/>.
 */
package org.dihedron.core.os;

import java.util.List;

import org.dihedron.core.regex.Regex;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Funto'
 */
public class HardDrivesTest {
	/**
	 * The logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(HardDrivesTest.class);

	/**
	 * Test method for {@link org.dihedron.core.os.HardDrives#listUnixFileSystems()}.
	 */
	@Test
	public void testListUnixFileSystems() {
		String [] lines = {
				"/dev/sda6 on / type ext4 (rw,errors=remount-ro)",
				"proc on /proc type proc (rw,noexec,nosuid,nodev)",
				"sysfs on /sys type sysfs (rw,noexec,nosuid,nodev)",
				"none on /sys/fs/cgroup type tmpfs (rw)",
				"none on /sys/fs/fuse/connections type fusectl (rw)",
				"none on /sys/kernel/debug type debugfs (rw)",
				"none on /sys/kernel/security type securityfs (rw)",
				"udev on /dev type devtmpfs (rw,mode=0755)",
				"devpts on /dev/pts type devpts (rw,noexec,nosuid,gid=5,mode=0620)",
				"tmpfs on /run type tmpfs (rw,noexec,nosuid,size=10%,mode=0755)",
				"none on /run/lock type tmpfs (rw,noexec,nosuid,nodev,size=5242880)",
				"none on /run/shm type tmpfs (rw,nosuid,nodev)",
				"none on /run/user type tmpfs (rw,noexec,nosuid,nodev,size=104857600,mode=0755)",
				"none on /sys/fs/pstore type pstore (rw)",
				"/dev/sda7 on /data type ext4 (rw)",
				"systemd on /sys/fs/cgroup/systemd type cgroup (rw,noexec,nosuid,nodev,none,name=systemd)",
				"gvfsd-fuse on /run/user/16777217/gvfs type fuse.gvfsd-fuse (rw,nosuid,nodev,user=d093154)",
				"/dev/sdb1 on /media/d093154/ARUBAKEY type vfat (rw,nosuid,nodev,uid=16777217,gid=16777216,shortname=mixed,dmask=0077,utf8=1,showexec,flush,uhelper=udisks2)"
		};
		
		Regex regex = new Regex("^\\s*(.*)\\s+(?:on)\\s+(.*)\\s+(?:type)\\s+(.*)\\s+(?:\\(.*\\))\\s*$");
		for(String line : lines) {
			if(regex.matches(line)) {
				logger.trace("line '{}' matches", line);
				List<String[]> matches = regex.getAllMatches(line);
				for(String [] group : matches) {
					logger.trace("-------------------------------- group --------------------------------");
					for(String string : group) {
						logger.trace("match: '{}'", string);
					}
				}
				logger.trace("-----------------------------------------------------------------------");
			}			
		}
	}
}
