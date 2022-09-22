<template>
	<div>
		<div style="display: flex; justify-content: space-between">
			<div>
				<el-input placeholder="搜索员工名" style="width: 300px; margin-right: 10px"
				          prefix-icon="el-icon-search" v-model="keywords" clearable @clear="initEmps"></el-input>
				<el-button type="primary" icon="el-icon-search" @click="normalSearch">搜索</el-button>
				<el-button type="primary"><i class="fa fa-angle-double-down"></i>高级搜索</el-button>
			</div>
			<div>
				<el-button type="success"><i class="fa fa-level-up"></i>导入数据</el-button>
				<el-button type="success"><i class="fa fa-level-down"></i>导出数据</el-button>
				<el-button type="success" icon="el-icon-plus" @click="showAddEmpView">添加用户</el-button>
			</div>
		</div>
		<div style="margin-top: 10px">
			<el-table
				ref="multipleTable"
				:data="emps"
				stripe
				border
				tooltip-effect="dark"
				style="width: 100%"
				@selection-change="handleSelectionChange">
				<el-table-column
					type="selection"
					width="55">
				</el-table-column>
				<el-table-column
					fixed
					prop="name"
					label="姓名"
					width="100">
				</el-table-column>
				<el-table-column
					prop="workID"
					label="工号"
					width="80"
					disabled>
				</el-table-column>
				<el-table-column
					prop="gender"
					label="性别"
					width="80">
				</el-table-column>
				<el-table-column
					prop="birthday"
					label="出生日期"
					width="95">
				</el-table-column>
				<el-table-column
					prop="idCard"
					label="身份证号"
					width="180">
				</el-table-column>
				<el-table-column
					prop="wedlock"
					label="婚姻状况"
					width="80">
				</el-table-column>
				<el-table-column
					prop="nation.name"
					label="民族"
					width="80">
				</el-table-column>
				<el-table-column
					prop="nativePlace"
					label="籍贯"
					width="80">
				</el-table-column>
				<el-table-column
					prop="politicsstatus.name"
					label="政治面貌"
					width="80">
				</el-table-column>
				<el-table-column
					prop="email"
					label="电子邮件"
					width="150">
				</el-table-column>
				<el-table-column
					prop="phone"
					label="电话号码"
					width="150">
				</el-table-column>
				<el-table-column
					prop="address"
					label="联系地址"
					width="180">
				</el-table-column>
				<el-table-column
					prop="department.name"
					label="所属部门"
					width="100">
				</el-table-column>
				<el-table-column
					prop="joblevel.name"
					label="职位"
					width="100">
				</el-table-column>
				<el-table-column
					prop="position.name"
					label="职称"
					width="100">
				</el-table-column>
				<el-table-column
					prop="engageForm"
					label="聘用形式"
					width="100">
				</el-table-column>
				<el-table-column
					prop="beginDate"
					label="入职日期"
					width="95">
				</el-table-column>
				<el-table-column
					prop="conversionTime"
					label="转正日期"
					width="95">
				</el-table-column>
				<el-table-column
					prop="beginContract"
					label="合同起始日期"
					width="95">
				</el-table-column>
				<el-table-column
					prop="endContract"
					label="合同截止日期"
					width="95">
				</el-table-column>
				<el-table-column
					prop="contractTerm"
					label="合同期限"
					width="80">
				</el-table-column>
				<el-table-column
					prop="tiptopDegree"
					label="最高学历"
					width="80">
				</el-table-column>
				<el-table-column label="操作" width="150" fixed="right">
					<template slot-scope="scope">
						<el-button
							size="mini" style="padding: 3px"
							@click="showEditEmpView(scope.row)">编辑
						</el-button>
						<el-button
							size="mini" style="padding: 3px"
							type="danger"
							@click="chakan(scope.row)">查看
						</el-button>
						<el-button
							size="mini" style="padding: 3px"
							type="danger"
							@click="handleDelete(scope.row)">删除
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
				background
				layout="sizes, prev, pager, next, jumper, ->, total, slot"
				style="display: flex; justify-content: flex-end"
				@current-change="currentChange"
				@size-change="sizeChange"
				:total="total">
			</el-pagination>
		</div>
		<el-dialog
			:title="dialogTitle"
			:visible.sync="dialogVisible"
			width="80%">
			<div>
				<el-form :model="emp" :rules="rules" ref="empForm">
					<el-row>
						<el-col :span="6">
							<el-form-item label="姓名:" prop="name">
								<el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="emp.name"
								          placeholder="请输入员工姓名"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="5">
							<el-form-item label="性别:" prop="gender">
								<el-radio-group v-model="emp.gender">
									<el-radio label="男">男</el-radio>
									<el-radio label="女">女</el-radio>
								</el-radio-group>
							</el-form-item>
						</el-col>
						<el-col :span="6">
							<el-form-item label="出生日期:" prop="birthday">
								<el-date-picker
									v-model="emp.birthday"
									size="mini"
									type="date"
									value-format="yyyy-MM-dd"
									style="width: 150px;"
									placeholder="出生日期">
								</el-date-picker>
							</el-form-item>
						</el-col>
						<el-col :span="7">
							<el-form-item label="政治面貌:" prop="politicId">
								<el-select v-model="emp.politicId" placeholder="政治面貌" size="mini" style="width: 200px;">
									<el-option
										v-for="item in politicsstatus"
										:key="item.id"
										:label="item.name"
										:value="item.id">
									</el-option>
								</el-select>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="6">
							<el-form-item label="民族:" prop="nationId">
								<el-select v-model="emp.nationId" placeholder="民族" size="mini" style="width: 150px;">
									<el-option
										v-for="item in nations"
										:key="item.id"
										:label="item.name"
										:value="item.id">
									</el-option>
								</el-select>
							</el-form-item>
						</el-col>
						<el-col :span="5">
							<el-form-item label="籍贯:" prop="nativePlace">
								<el-input size="mini" style="width: 120px" prefix-icon="el-icon-edit"
								          v-model="emp.nativePlace" placeholder="请输入籍贯"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="6">
							<el-form-item label="电子邮箱:" prop="email">
								<el-input size="mini" style="width: 150px" prefix-icon="el-icon-message"
								          v-model="emp.email" placeholder="请输入电子邮箱"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="7">
							<el-form-item label="联系地址:" prop="address">
								<el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit"
								          v-model="emp.address" placeholder="请输入联系地址"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="6">
							<el-form-item label="职位:" prop="posId">
								<el-select v-model="emp.posId" placeholder="职位" size="mini" style="width: 150px;">
									<el-option
										v-for="item in positions"
										:key="item.id"
										:label="item.name"
										:value="item.id">
									</el-option>
								</el-select>
							</el-form-item>
						</el-col>
						<el-col :span="5">
							<el-form-item label="职称:" prop="jobLevelId">
								<el-select v-model="emp.jobLevelId" placeholder="职称" size="mini" style="width: 150px;">
									<el-option
										v-for="item in jobLevels"
										:key="item.id"
										:label="item.name"
										:value="item.id">
									</el-option>
								</el-select>
							</el-form-item>
						</el-col>
						<el-col :span="6">
							<el-form-item label="所属部门:" prop="departmentId">
								<el-popover
									placement="right"
									title="请选择部门"
									width="200"
									trigger="manual"
									v-model="popVisible">
									<el-tree default-expand-all :data="allDeps" :props="defaultProps"
									         :expand-on-click-node="false"
									         @node-click="handleNodeClick"></el-tree>
									<div slot="reference"
									     style="width: 150px;display: inline-flex;font-size: 13px;border: 1px solid #dedede;height: 26px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box"
									     @click="showDepView">{{ inputDepName }}
									</div>
								</el-popover>
							</el-form-item>
						</el-col>
						<el-col :span="7">
							<el-form-item label="电话号码:" prop="phone">
								<el-input size="mini" style="width: 200px" prefix-icon="el-icon-phone"
								          v-model="emp.phone" placeholder="电话号码"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="6">
							<el-form-item label="工号:" prop="workID">
								<el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit"
								          v-model="emp.workID" placeholder="工号" disabled></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="5">
							<el-form-item label="学历:" prop="tiptopDegree">
								<el-select v-model="emp.tiptopDegree" placeholder="学历" size="mini"
								           style="width: 150px;">
									<el-option
										v-for="item in tiptopDegrees"
										:key="item"
										:label="item"
										:value="item">
									</el-option>
								</el-select>
							</el-form-item>
						</el-col>
						<el-col :span="6">
							<el-form-item label="毕业院校:" prop="school">
								<el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit"
								          v-model="emp.school" placeholder="毕业院校名称"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="7">
							<el-form-item label="专业名称:" prop="specialty">
								<el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit"
								          v-model="emp.specialty" placeholder="请输入专业名称"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="6">
							<el-form-item label="入职日期:" prop="beginDate">
								<el-date-picker
									v-model="emp.beginDate"
									size="mini"
									type="date"
									value-format="yyyy-MM-dd"
									style="width: 130px;"
									placeholder="入职日期">
								</el-date-picker>
							</el-form-item>
						</el-col>
						<el-col :span="6">
							<el-form-item label="转正日期:" prop="conversionTime">
								<el-date-picker
									v-model="emp.conversionTime"
									size="mini"
									type="date"
									value-format="yyyy-MM-dd"
									style="width: 130px;"
									placeholder="转正日期">
								</el-date-picker>
							</el-form-item>
						</el-col>
						<el-col :span="6">
							<el-form-item label="合同起始日期:" prop="beginContract">
								<el-date-picker
									v-model="emp.beginContract"
									size="mini"
									type="date"
									value-format="yyyy-MM-dd"
									style="width: 130px;"
									placeholder="合同起始日期">
								</el-date-picker>
							</el-form-item>
						</el-col>
						<el-col :span="6">
							<el-form-item label="合同终止日期:" prop="endContract">
								<el-date-picker
									v-model="emp.endContract"
									size="mini"
									type="date"
									value-format="yyyy-MM-dd"
									style="width: 150px;"
									placeholder="合同终止日期">
								</el-date-picker>
							</el-form-item>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="8">
							<el-form-item label="身份证号码:" prop="idCard">
								<el-input size="mini" style="width: 180px" prefix-icon="el-icon-edit"
								          v-model="emp.idCard" placeholder="请输入身份证号码"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="8">
							<el-form-item label="聘用形式:" prop="engageForm">
								<el-radio-group v-model="emp.engageForm">
									<el-radio label="劳动合同">劳动合同</el-radio>
									<el-radio label="劳务合同">劳务合同</el-radio>
								</el-radio-group>
							</el-form-item>
						</el-col>
						<el-col :span="8">
							<el-form-item label="婚姻状况:" prop="wedlock">
								<el-radio-group v-model="emp.wedlock">
									<el-radio label="已婚">已婚</el-radio>
									<el-radio label="未婚">未婚</el-radio>
									<el-radio label="离异">离异</el-radio>
								</el-radio-group>
							</el-form-item>
						</el-col>
					</el-row>
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
			    <el-button @click="dialogVisible = false">取 消</el-button>
			    <el-button type="primary" @click="handleAddEmp">确 定</el-button>
		    </span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	name: "EmpBasic",
	data() {
		return {
			dialogTitle: '',
			emp: {
				name: "江南一点雨",
				gender: "男",
				birthday: "1989-12-31",
				idCard: "610122199001011256",
				wedlock: "已婚",
				nationId: 1,
				nativePlace: "陕西",
				politicId: 13,
				email: "laowang@qq.com",
				phone: "18565558897",
				address: "深圳市南山区",
				departmentId: null,
				jobLevelId: 9,
				posId: 29,
				engageForm: "劳务合同",
				tiptopDegree: "本科",
				specialty: "信息管理与信息系统",
				school: "深圳大学",
				beginDate: "2017-12-31",
				workState: "在职",
				workID: "00000001",
				contractTerm: 2.0,
				conversionTime: "2018-03-31",
				notWorkDate: null,
				beginContract: "2017-12-31",
				endContract: "2019-12-31",
				workAge: null,
			},
			emps: [],
			total: 0,
			page: 1,
			size: 10,
			keywords: '',
			dialogVisible: false,
			tiptopDegrees: ["本科", "大专", "硕士", "博士", "高中", "初中", "小学", "其他"],
			jobLevels: [],
			positions: [],
			nations: [],
			politicsstatus: [],
			allDeps: [],
			inputDepName: '',
			popVisible: false,
			defaultProps: {
				children: 'children',
				label: 'name'
			},
			rules: {
				name: [{required: true, message: '请输入用户名', trigger: 'blur'}],
				gender: [{required: true, message: '请输入性别', trigger: 'blur'}],
				birthday: [{required: true, message: '请输入出生日期', trigger: 'blur'}],
				idCard: [{required: true, message: '请输入身份证号码', trigger: 'blur'}, {
					pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
					message: '身份证号码格式不正确',
					trigger: 'blur'
				}],
				wedlock: [{required: true, message: '请输入婚姻状况', trigger: 'blur'}],
				nationId: [{required: true, message: '请输入您组', trigger: 'blur'}],
				nativePlace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
				politicId: [{required: true, message: '请输入政治面貌', trigger: 'blur'}],
				email: [{required: true, message: '请输入邮箱地址', trigger: 'blur'}, {
					type: 'email',
					message: '邮箱格式不正确',
					trigger: 'blur'
				}],
				phone: [{required: true, message: '请输入电话号码', trigger: 'blur'}],
				address: [{required: true, message: '请输入员工地址', trigger: 'blur'}],
				departmentId: [{required: true, message: '请输入部门名称', trigger: 'blur'}],
				jobLevelId: [{required: true, message: '请输入职称', trigger: 'blur'}],
				posId: [{required: true, message: '请输入职位', trigger: 'blur'}],
				engageForm: [{required: true, message: '请输入聘用形式', trigger: 'blur'}],
				tiptopDegree: [{required: true, message: '请输入学历', trigger: 'blur'}],
				specialty: [{required: true, message: '请输入专业', trigger: 'blur'}],
				school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
				beginDate: [{required: true, message: '请输入入职日期', trigger: 'blur'}],
				workState: [{required: true, message: '请输入工作状态', trigger: 'blur'}],
				workID: [{required: true, message: '请输入工号', trigger: 'blur'}],
				contractTerm: [{required: true, message: '请输入合同期限', trigger: 'blur'}],
				conversionTime: [{required: true, message: '请输入转正日期', trigger: 'blur'}],
				notworkDate: [{required: true, message: '请输入离职日期', trigger: 'blur'}],
				beginContract: [{required: true, message: '请输入合同起始日期', trigger: 'blur'}],
				endContract: [{required: true, message: '请输入合同结束日期', trigger: 'blur'}],
				workAge: [{required: true, message: '请输入工龄', trigger: 'blur'}],
			}
		}
	},
	mounted() {
		this.initEmps()
		this.initData()
	},
	methods: {
		initEmps() {
			this.getRequest("/employee/basic/?page=" + this.page + "&size=" + this.size + "&keywords=" + this.keywords).then(resp => {
				if (resp) {
					this.emps = resp.data
					this.total = resp.total
				}
			})
		},
		currentChange(page) {
			this.page = page
			this.initEmps()
		},
		sizeChange(size) {
			this.size = size
			this.initEmps()
		},
		normalSearch() {
			this.initEmps()
		},
		showAddEmpView() {
			this.emptyEmp()
			this.initPositions()
			this.getMaxWorkID()
			this.dialogTitle = "新增员工"
			this.dialogVisible = true
		},
		showEditEmpView(data) {
			this.initPositions()
			this.dialogTitle = "编辑员工"
			this.inputDepName = data.department.name
			this.emp = data
			this.dialogVisible = true
		},
		initData() {//存放不怎么变化的数据
			console.log(window.sessionStorage.getItem("nations"))
			if (!window.sessionStorage.getItem("nations")) {
				this.getRequest("/employee/basic/nations").then(resp => {
					if (resp) {
						this.nations = resp
						window.sessionStorage.setItem("nations", JSON.stringify(resp))
					}
				})
			} else {
				this.nations = JSON.parse(window.sessionStorage.getItem("nations"))
			}

			if (!window.sessionStorage.getItem("jobLevels")) {
				this.getRequest("/employee/basic/jobLevels").then(resp => {
					if (resp) {
						this.jobLevels = resp
						window.sessionStorage.setItem("jobLevels", JSON.stringify(resp))
					}
				})
			} else {
				this.jobLevels = JSON.parse(window.sessionStorage.getItem("jobLevels"))
			}

			if (!window.sessionStorage.getItem("politicsstatus")) {
				this.getRequest("/employee/basic/politicsstatus").then(resp => {
					if (resp) {
						this.politicsstatus = resp
						window.sessionStorage.setItem("politicsstatus", JSON.stringify(resp))
					}
				})
			} else {
				this.politicsstatus = JSON.parse(window.sessionStorage.getItem("politicsstatus"))
			}

			if (!window.sessionStorage.getItem("allDeps")) {
				this.getRequest("/employee/basic/departments").then(resp => {
					if (resp) {
						this.allDeps = resp
						window.sessionStorage.setItem("allDeps", JSON.stringify(resp))
					}
				})
			} else {
				this.allDeps = JSON.parse(window.sessionStorage.getItem("allDeps"))
			}
		},
		initPositions() {
			this.getRequest("/employee/basic/positions").then(resp => {
				this.positions = resp
			})
		},
		getMaxWorkID() {
			this.getRequest("/employee/basic/maxWorkID").then(resp => {
				this.emp.workID = resp.obj
			})
		},
		showDepView() {
			this.popVisible = !this.popVisible
		},
		emptyEmp() {
			this.emp = {
				name: "",
				gender: "",
				birthday: "",
				idCard: "",
				wedlock: "",
				nationId: 1,
				nativePlace: "",
				politicId: 13,
				email: "",
				phone: "",
				address: "",
				departmentId: null,
				jobLevelId: 9,
				posId: 29,
				engageForm: "",
				tiptopDegree: "",
				specialty: "",
				school: "",
				beginDate: "",
				workState: "",
				workID: "",
				contractTerm: 2.0,
				conversionTime: "",
				notWorkDate: null,
				beginContract: "",
				endContract: "",
				workAge: null,
			}
			this.inputDepName = ""
		},
		handleNodeClick(data) {
			this.inputDepName = data.name
			this.emp.departmentId = data.id
			this.popVisible = !this.popVisible
		},
		handleAddEmp() {
			if (!this.emp.id) {//如果emp字段的id没有数据，说明确认的请求来自添加员工，否则来自更新员工
				this.$refs.empForm.validate(valid => {
					if (valid) {
						this.postRequest("/employee/basic/", this.emp).then(resp => {
							if (resp) {
								this.dialogVisible = false
								this.initEmps()
							}
						})
					}
				})
			} else {
				this.$refs.empForm.validate(valid => {
					if (valid) {
						this.putRequest("/employee/basic/", this.emp).then(resp => {
							if (resp) {
								this.dialogVisible = false
								this.initEmps()
							}
						})
					}
				})
			}
		},
		handleSelectionChange() {
		},
		handleDelete(data) {
			this.$confirm('此操作将永久删除「' + data.name + ' 」，是否继续?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				this.deleteRequest("/employee/basic/" + data.id).then(resp => {
					if (resp) {
						this.initEmps()
					}
				});
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

<style scoped>

</style>
