<script setup>
import { computed, onMounted, ref } from 'vue'
import { api } from '../api/mock'
const loading = ref(false); const failed = ref(false); const data = ref([]); const selected = ref(null)
function flatten(nodes, result = []) { nodes.forEach(node => { result.push(node); if (node.children?.length) flatten(node.children, result) }); return result }
const allNodes = computed(() => flatten(data.value)); const departmentCount = computed(() => allNodes.value.filter(item => item.type === 'department').length); const branchCount = computed(() => allNodes.value.filter(item => item.type === 'branch').length)
async function load() { loading.value = true; failed.value = false; try { data.value = await api.listDepartments(); selected.value = data.value[0] || null } catch { failed.value = true } finally { loading.value = false } }
function choose(node) { selected.value = node }
const typeLabel = type => type === 'company' ? '总公司' : type === 'branch' ? '分公司' : '部门'
const typeTag = type => type === 'company' ? '' : type === 'branch' ? 'warning' : 'primary'
onMounted(load)
</script>

<template>
  <div><div class="page-heading"><div><h2>部门架构</h2><p>总公司统一管理各部门和分公司，分公司下继续维护本级部门。</p></div><el-button @click="load">刷新</el-button></div>
    <el-card class="department-card"><el-skeleton v-if="loading" :rows="7" animated /><el-result v-else-if="failed" icon="error" title="部门加载失败" sub-title="请检查网络后重试"><template #extra><el-button type="primary" @click="load">重新加载</el-button></template></el-result><el-empty v-else-if="!data.length" description="暂无部门数据" /><div v-else class="department-layout"><div class="department-tree"><div class="tree-title"><b>组织目录</b><span>{{ branchCount }} 个分公司 · {{ departmentCount }} 个部门</span></div><el-tree :data="data" node-key="id" default-expand-all highlight-current @node-click="choose"><template #default="{ data: item }"><div class="tree-node"><span class="tree-node-name">{{ item.name }}</span><span class="tree-node-meta"><el-tag size="small" :type="typeTag(item.type)" effect="plain">{{ typeLabel(item.type) }}</el-tag><small v-if="item.manager">{{ item.manager }}</small></span></div></template></el-tree></div><div v-if="selected" class="department-detail"><div class="department-detail-head"><div class="department-icon">{{ selected.type === 'branch' ? '分' : selected.type === 'company' ? '总' : '部' }}</div><div><h3>{{ selected.name }}</h3><p>{{ selected.path }}</p></div><el-tag :type="typeTag(selected.type)" effect="plain">{{ typeLabel(selected.type) }}</el-tag></div><el-descriptions :column="1" border><el-descriptions-item label="部门经理">{{ selected.manager || '未设置' }}</el-descriptions-item><el-descriptions-item label="上级部门">{{ selected.parentName || '无' }}</el-descriptions-item><el-descriptions-item label="所属分公司">{{ selected.branch || '总公司' }}</el-descriptions-item><el-descriptions-item label="员工数量">{{ selected.employeeCount ?? 0 }} 人</el-descriptions-item></el-descriptions><el-alert class="department-note" title="部门管理员只能维护本部门员工；跨部门管理由超级管理员负责。" type="info" :closable="false" /></div></div></el-card>
  </div>
</template>
