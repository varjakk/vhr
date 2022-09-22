<template>
	<div>
		<div>
			<el-input
				v-model="inputPos.name"
				class="posInput"
				placeholder="添加职位..."
				prefix-icon="el-icon-plus"
				size="small"
				@keydown.enter.native="initPositions">
			</el-input>
			<el-button icon="el-icon-plus" size="small" type="primary" @click="addPosition">添加</el-button>
		</div>
		<div>
			<el-table
				:data="positions"
				border
				class="posTable"
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
					label="职位名称"
					prop="name"
					width="180">
				</el-table-column>
				<el-table-column
					label="创建时间"
					prop="createDate"
					width="150">
				</el-table-column>
				<el-table-column
					label="启用状态"
					width="80">
					<template slot-scope="scope">
						<el-tag v-if="scope.row.enabled" type="success">已启用</el-tag>
						<el-tag v-else type="danger">未启用</el-tag>
					</template>
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
			title="修改名称"
			width="30%">
			<div>
				<el-tag>职位名称</el-tag>
				<el-input v-model="updatePos.name" class="updatePosInput" size="small"></el-input>
			</div>
			<div>
				<el-tag>是否启用</el-tag>
				<el-switch v-model="updatePos.enabled" active-text="启用" inactive-text="禁用"></el-switch>
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
	name: "PosMana",
	data() {
		return {
			positions: [],
			inputPos: {
				name: ''
			},
			dialogVisible: false,
			updatePos: {
				name: '',
				enabled: false
			},
			multipleSelection: [],
		}
	},
	mounted() {
		this.initPositions()
	},
	methods: {
		initPositions() {
			this.getRequest("/system/basic/pos/").then(resp => {
				if (resp) {
					this.positions = resp
				}
			})
		},
		addPosition() {
			if (this.inputPos.name) {
				this.postRequest("/system/basic/pos/", this.inputPos).then(resp => {
					if (resp) {//若有返回数据，则添加成功，调用刷新表格的方法
						this.initPositions()
						this.inputPos = {}
					}
				})
			} else {
				this.$message.error("职位名称不可以为空")
			}
		},
		showDialog(index, data) {
			Object.assign(this.updatePos, data)
			this.dialogVisible = true
		},
		handleUpdate() {
			this.putRequest("/system/basic/pos/", this.updatePos).then(resp => {
				if (resp) {//若有返回数据，则添加成功，调用刷新表格的方法
					this.initPositions()
					// this.updatePos = {}
					this.dialogVisible = false
				}
			})
		},
		handleDelete(index, data) {
			this.$confirm('是否删除' + data.name + '职位？', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				this.deleteRequest("/system/basic/pos/" + data.id).then(resp => {
					if (resp) {
						this.initPositions()
					}
				})
			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消删除'
				});
			});
		},
		handleSelectionChange(val) {
			this.multipleSelection = val
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
				this.deleteRequest("/system/basic/pos/" + ids).then(resp => {
					if (resp) {
						this.initPositions()
					}
				})
			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消删除'
				});
			});
		},
	}

}
</script>

<style>
.posInput {
	width: 300px;
	margin-right: 8px;
}

.posTable {
	margin-top: 10px;
}

.updatePosInput {
	width: 200px;
	margin-left: 8px
}

</style>
