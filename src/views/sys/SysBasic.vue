<template>
	<div>
		<el-tabs v-model="activeName" type="card">
			<el-tab-pane label="部门管理" name="depmana">
				<DepMana/>
			</el-tab-pane>
			<el-tab-pane label="职位管理" name="posmana">
				<PosMana/>
			</el-tab-pane>
			<el-tab-pane label="职称管理" name="joblevelmana">
				<JobLevelMana/>
			</el-tab-pane>
			<el-tab-pane label="奖惩管理" name="ecmana">
				<EcMana/>
			</el-tab-pane>
			<el-tab-pane label="权限组" name="permissionmana">
				<PermissionMana/>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>

<script>
import DepMana from "@/components/sys/basic/DepMana";
import EcMana from "@/components/sys/basic/EcMana";
import JobLevelMana from "@/components/sys/basic/JobLevelMana";
import PermissionMana from "@/components/sys/basic/PermissionMana";
import PosMana from "@/components/sys/basic/PosMana";

export default {
	name: "SysBasic",
	data() {
		return {
			activeName: "depmana",
		}
	},
	components: {
		DepMana, EcMana, JobLevelMana, PermissionMana, PosMana
	}

}
</script>

<style scoped>

</style>
