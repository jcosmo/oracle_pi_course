### BEGIN INIT INFO
# Provides: javame-ams-daemon
# Required-Start:
# Required-Stop:
# Default-Start: 2 3 4 5
# Default-Stop: 0 1 6
# Short-Description: Java ME User Test
# Description: The usertest.sh script from javame
### END INIT INFO

. /lib/lsb/init-functions

case $1 in
 start)
  /home/pi/javame8/bin/usertest.sh &
  ;;
 stop)
  pkill usertest.sh
  ;;
esac

