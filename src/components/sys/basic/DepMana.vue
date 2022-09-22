<template>
	<div style="width: 500px">
		<el-input
			v-model="filterText"
			placeholder="输入部门名称搜索部门"
			prefix-icon="el-icon-search">
		</el-input>

		<el-tree
			ref="tree"
			:data="deps"
			:expand-on-click-node="false"
			:filter-node-method="filterNode"
			:props="defaultProps"
			class="filter-tree">
			<span slot-scope="{ node, data }" class="custom-tree-node"
			      style="display:flex; justify-content: space-between; width: 100%">
                <span>{{ data.name }}</span>
		        <span>
		            <el-button
			            class="depButton"
			            type="primary"
			            @click="() => showAddDialog(data)">
		            添加部门
			          </el-button>
			        <el-button
				        class="depButton"
				        type="danger"
				        @click="() => delDep(data)">
			            删除部门
			          </el-button>
		        </span>
            </span>
		</el-tree>
		<el-dialog
			:visible.sync="dialogVisible"
			title="添加部门"
			width="30%">
			<div>
				<tr>
					<td>
						<el-tag>上级部门</el-tag>
					</td>
					<td>
						{{ parentName }}
					</td>
				</tr>
				<tr>
					<td>
						<el-tag>部门名称</el-tag>
					</td>
					<td>
						<el-input v-model="addDepInput.name" placeholder="请输入部门名称"/>

					</td>
				</tr>
			</div>
			<span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addDep">确 定</el-button>
  </span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	name: "DepMana",
	watch: {
		filterText(val) {
			this.$refs.tree.filter(val);
		}
	},
	mounted() {
		this.initDeps();
	},
	methods: {
		initDeps() {
			this.getRequest("/system/basic/department/").then(resp => {
				if (resp) {
					this.deps = resp;
				}
			})
		},
		filterNode(value, data) {
			if (!value) return true;
			return data.name.indexOf(value) !== -1;
		},
		showAddDialog(data) {
			this.parentName = data.name;
			this.addDepInput.parentId = data.id;
			this.dialogVisible = true;
		},
		addDep() {
			this.postRequest("/system/basic/department/", this.addDepInput).then(resp => {
				if (resp) {
					this.addDep2Deps(this.deps, resp.obj)
					this.dialogVisible = false
					this.addDepInput = []
					this.parentName = ''
				}
			})
		},
		//deps：部门树，dep：添加完成后返回的对象
		addDep2Deps(deps, dep) {
			for (let i = 0; i < deps.length; i++) {
				let tree = deps[i];
				if (tree.id === dep.parentId) {
					tree.children = tree.children.concat(dep)
					if (tree.children.length > 0) {
						tree.parent = true
					}
					return
				} else {
					this.addDep2Deps(tree.children, dep)//递归查找符合要求的部门
				}
			}
		},
		delDep(data) {
			if (data.parent) {
				this.$message.error("该部门为父部门，删除失败")
			} else {
				this.$confirm('是否删除【' + data.name + "】部门", '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.deleteRequest("system/basic/department/" + data.id).then(resp => {
						if (resp) {
							this.removeDepFromDeps(null, this.deps, data.id)
						}
					})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				})
			}
		},
		removeDepFromDeps(p, deps, id) {
			for (let i = 0; i < deps.length; i++) {
				let tree = deps[i]
				if (tree.id === id) {
					deps.splice(i, 1)
					if (deps.length === 0) {//若删除后子部门为空，需要将该部门的parent属性置为false
						p.parent = false
					}
					return
				} else {
					this.removeDepFromDeps(tree, tree.children, id)
				}
			}
		}
	},

	data() {
		return {
			filterText: '',
			deps: [],
			defaultProps: {
				children: 'children',
				label: 'name'
			},
			addDepInput: {
				name: '',
				parentId: -1
			},
			parentName: '',
			dialogVisible: false,
		};
	}
}
</script>

<style scoped>
.depButton {
	padding: 1px;
}
</style>
