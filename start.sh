#!/bin/bash

# ============================================================
#  荣成餐饮店管理系统 - 一键启动脚本
#  Restaurant Management System - One-click Start Script
# ============================================================

set -e

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
BACKEND_DIR="$PROJECT_DIR/backend"
FRONTEND_DIR="$PROJECT_DIR/frontend"
BACKEND_LOG="/tmp/restaurant-backend.log"
FRONTEND_LOG="/tmp/restaurant-frontend.log"
BACKEND_PID=""
FRONTEND_PID=""

cleanup() {
    echo ""
    echo -e "${YELLOW}正在关闭服务...${NC}"
    if [ -n "$FRONTEND_PID" ] && kill -0 "$FRONTEND_PID" 2>/dev/null; then
        kill "$FRONTEND_PID" 2>/dev/null
        echo -e "${GREEN}前端服务已停止${NC}"
    fi
    if [ -n "$BACKEND_PID" ] && kill -0 "$BACKEND_PID" 2>/dev/null; then
        kill "$BACKEND_PID" 2>/dev/null
        echo -e "${GREEN}后端服务已停止${NC}"
    fi
    # Also kill any child processes
    pkill -f "spring-boot:run" 2>/dev/null || true
    pkill -f "vite" 2>/dev/null || true
    echo -e "${GREEN}所有服务已关闭${NC}"
    exit 0
}

trap cleanup SIGINT SIGTERM

echo -e "${CYAN}"
echo "=============================================="
echo "  荣成餐饮店管理系统"
echo "  Restaurant Management System"
echo "=============================================="
echo -e "${NC}"

# Check prerequisites
echo -e "${YELLOW}[1/4] 检查运行环境...${NC}"

if ! command -v java &> /dev/null; then
    echo -e "${RED}错误: 未找到 Java，请安装 JDK 8+${NC}"
    exit 1
fi
echo -e "  ${GREEN}✓${NC} Java: $(java -version 2>&1 | head -1)"

if ! command -v mvn &> /dev/null; then
    echo -e "${RED}错误: 未找到 Maven，请安装 Maven 3.6+${NC}"
    exit 1
fi
echo -e "  ${GREEN}✓${NC} Maven: $(mvn -version 2>&1 | head -1)"

if ! command -v node &> /dev/null; then
    echo -e "${RED}错误: 未找到 Node.js，请安装 Node.js 16+${NC}"
    exit 1
fi
echo -e "  ${GREEN}✓${NC} Node.js: $(node --version)"

if ! command -v npm &> /dev/null; then
    echo -e "${RED}错误: 未找到 npm${NC}"
    exit 1
fi
echo -e "  ${GREEN}✓${NC} npm: $(npm --version)"

# Start backend
echo -e "${YELLOW}[2/4] 启动后端服务 (Spring Boot)...${NC}"
cd "$BACKEND_DIR"

# Remove old database to start fresh with seed data (comment out to keep data)
# rm -f restaurant.db

mvn spring-boot:run > "$BACKEND_LOG" 2>&1 &
BACKEND_PID=$!

# Wait for backend to be ready
echo -n "  等待后端启动"
for i in $(seq 1 30); do
    if curl -s http://localhost:8080/login > /dev/null 2>&1; then
        echo ""
        echo -e "  ${GREEN}✓${NC} 后端服务已启动 (http://localhost:8080)"
        break
    fi
    if [ $i -eq 30 ]; then
        echo ""
        echo -e "${RED}错误: 后端服务启动超时，请检查日志: $BACKEND_LOG${NC}"
        cleanup
        exit 1
    fi
    echo -n "."
    sleep 1
done

# Start frontend
echo -e "${YELLOW}[3/4] 启动前端服务 (Vue 3)...${NC}"
cd "$FRONTEND_DIR"

# Install dependencies if needed
if [ ! -d "node_modules" ]; then
    echo "  安装前端依赖..."
    npm install --silent
fi

npx vite --host 0.0.0.0 > "$FRONTEND_LOG" 2>&1 &
FRONTEND_PID=$!

# Wait for frontend to be ready
echo -n "  等待前端启动"
for i in $(seq 1 15); do
    if curl -s http://localhost:5173 > /dev/null 2>&1; then
        echo ""
        echo -e "  ${GREEN}✓${NC} 前端服务已启动 (http://localhost:5173)"
        break
    fi
    if [ $i -eq 15 ]; then
        echo ""
        echo -e "${YELLOW}警告: 前端服务可能未完全启动，请检查日志: $FRONTEND_LOG${NC}"
    fi
    echo -n "."
    sleep 1
done

echo ""
echo -e "${GREEN}[4/4] 启动完成！${NC}"
echo ""
echo -e "${CYAN}=============================================="
echo "  服务已就绪"
echo "=============================================="
echo -e "${NC}"
echo -e "  前端页面:  ${GREEN}http://localhost:5173${NC}"
echo -e "  后端接口:  ${GREEN}http://localhost:8080${NC}"
echo ""
echo -e "  演示账号:"
echo -e "    管理员  ${CYAN}admin${NC}    / ${CYAN}admin${NC}"
echo -e "    前台    ${CYAN}counter${NC}  / ${CYAN}counter${NC}"
echo -e "    采购    ${CYAN}procure${NC}  / ${CYAN}procure${NC}"
echo ""
echo -e "  按 ${RED}Ctrl+C${NC} 停止所有服务"
echo -e "${CYAN}==============================================${NC}"
echo ""

# Wait for both processes
wait
