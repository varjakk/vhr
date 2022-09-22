<template>
	<div>
		<div>
			<el-input
				v-model="jobLevel.name"
				class="jobLevelInput"
				placeholder="职称名称..."
				prefix-icon="el-icon-plus"
				size="small">
			</el-input>
			<el-select v-model="jobLevel.titleLevel" placeholder="请选择职称等级" size="small" style="margin-right: 8px">
				<el-option
					v-for="item in titleLevels"
					:key="item"
					:label="item"
					:value="item">
				</el-option>
			</el-select>
			<el-button icon="el-icon-plus" size="small" type="primary" @click="addJobLevel">添加</el-button>
		</div>
		<div>
			<el-table
				:data="jobLevelTables"
				border
				class="jobLevelTable"
				size="small"
				stripe
				style="width: 100%"
				@selection-change="handleSelectionChange">
				<el-table-column
					type="selection"
					width="55">
				</el-table-column>
				<el-table-column
					label="编号"
					prop="id"
					width="55">
				</el-table-column>
				<el-table-column
					label="职称名称"
					prop="name"
					width="100">
				</el-table-column>
				<el-table-column
					label="职称级别"
					prop="titleLevel"
					width="100">
				</el-table-column>
				<el-table-column
					label="启用状态"
					width="80">
					<template slot-scope="scope">
						<el-tag v-if="scope.row.enabled" type="success">已启用</el-tag>
						<el-tag v-else type="danger">未启用</el-tag>
					</template>
				</el-table-column>
				<el-table-column
					label="创建时间"
					prop="createDate"
					width="150">
				</el-table-column>
				<el-table-column label="操作">
					<template slot-scope="scope">
						<el-button
							size="small"
							@click="showDialog(scope.$index, scope.row)">编辑
						</el-button>
						<el-button
							size="small"
							type="danger"
							@click="handleDelete(scope.$index, scope.row)">删除
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-button :disabled="multipleSelection.length===0" size="small" style="margin-top:8px" type="danger"
			           @click="handleDeleteMany">批量删除
			</el-button>
		</div>
		<el-dialog
			:visible.sync="dialogVisible"
			title="修改职称"
			width="30%">
			<div>
				<table>
					<tr>
						<td>
							<el-tag>职称名称</el-tag>
						</td>
						<td>
							<el-input v-model="updateJobLevel.name" size="small">职称名</el-input>
						</td>
					</tr>
					<tr>
						<td>
							<el-tag>职称级别</el-tag>
						</td>
						<td>
							<el-select v-model="updateJobLevel.titleLevel" placeholder="请选择" size="small"
							           style="margin-right: 8px">
								<el-option
									v-for="item in titleLevels"
									:key="item"
									:label="item"
									:value="item">
								</el-option>
							</el-select>
						</td>
					</tr>
					<tr>
						<td>
							<el-tag>是否启用</el-tag>
						</td>
						<td>
							<el-switch v-model="updateJobLevel.enabled" active-text="启用" inactive-text="禁用"></el-switch>
						</td>
					</tr>
				</table>
			</div>
			<span slot="footer" class="dialog-footer">
			    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
			    <el-button size="small" type="primary" @click="handleUpdate">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>

export default {
	name: "JobLevelMana",
	data() {
		return {
			jobLevel: {
				name: '',
				titleLevel: '',
			},
			updateJobLevel: {
				name: '',
				titleLevel: '',
				enabled: false,
			},
			titleLevels: [
				"正高级",
				"副高级",
				"中级",
				"初级",
				"员级"
			],
			jobLevelTables: [],
			multipleSelection: [],
			dialogVisible: false
		}
	},
	mounted() {
		this.initJobLevels()
	},
	methods: {
		initJobLevels() {
			this.getRequest("/system/basic/joblevel/").then(resp => {
				this.jobLevelTables = resp
			})
		},
		addJobLevel() {
			if (this.jobLevel.name && this.jobLevel.titleLevel) {
				this.postRequest("/system/basic/joblevel/", this.jobLevel).then(resp => {
					if (resp) {
						this.initJobLevels()
						this.jobLevel = {}
					}
				})
			} else {
				this.$message.error("职称名或者职称等级不可为空")
			}
		},
		handleSelectionChange(val) {
			this.multipleSelection = val
		},
		showDialog(index, data) {
			Object.assign(this.updateJobLevel, data)
			this.dialogVisible = true
		},
		handleUpdate() {
			this.putRequest("/system/basic/joblevel/", this.updateJobLevel).then(resp => {
				if (resp) {
					this.initJobLevels()
					this.dialogVisible = false
				}
			})
		},
		handleDelete(index, data) {
			this.$confirm('是否删除' + this.jobLevel.name + '职位', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				this.deleteRequest("/system/basic/joblevel/" + data.id).then(resp => {
					if (resp) {
						this.initJobLevels()
					}
				})
			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消删除'
				});
			});
		},
		handleDeleteMany() {
			this.$confirm('是否删除' + this.multipleSelection.length + '项数据', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				let ids = '?'
				this.multipleSelection.forEach(item => {
					ids += "ids=" + item.id + "&"
				})
				this.deleteRequest("/system/basic/joblevel/" + ids).then(resp => {
					if (resp) {
						this.initJobLevels()
					}
				})
			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消删除'
				});
			});
		}
	}
}
</script>

<style>
.jobLevelInput {
	width: 300px;
	margin-right: 8px;
}

.jobLevelTable {
	margin-top: 10px;
}
</style>
