<?xml version="1.0" encoding="UTF-8"?>
<mspec:mspec xmlns:mspec="http://www.eclipse.org/buckminster/MetaData-1.0" installLocation="" materializer="p2" name="build.mspec" url="build.cquery">
  <!-- already checked out before Buckminster is called -->
  <mspec:mspecNode namePattern="^org\.eclipse\.mdt\.papyrus\.releng\.buckminster$" exclude="true"/>
  <mspec:property key="target.os" value="*"/>
  <mspec:property key="target.ws" value="*"/>
  <mspec:property key="target.arch" value="*"/>
  <mspec:mspecNode materializer="workspace" namePattern="" filter="(buckminster.source=true)"/>
</mspec:mspec>
