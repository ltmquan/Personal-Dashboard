# Dashboard Frontend

Personal dashboard interface built with Vue 3 and Vite.

## 🚀 Tech Stack

- **Vue 3** - Progressive JavaScript framework
- **Vite** - Fast build tool and dev server
- **Axios** - HTTP client for API calls
- **JavaScript** - ES6+ features
- **CSS3** - Modern styling with Flexbox/Grid

## 📋 Features

- **Task Management** - Create, edit, complete, and delete tasks
- **Calendar View** - Weekly calendar showing events and tasks with due dates
- **Weather Widget** - Real-time weather information
- **Responsive Design** - Clean, modern interface
- **Real-time Updates** - Syncs with backend API

## 🛠️ Setup Instructions

### Prerequisites

- Node.js 18+ and npm
- Backend API running at `http://localhost:8080`

### Installation

1. **Clone the repository**
```bash
git clone <your-repo-url>
cd dashboard-frontend
```

2. **Install dependencies**
```bash
npm install
```

3. **Start development server**
```bash
npm run dev
```

Application will start at `http://localhost:5173`

4. **Verify it's running**

Open browser to `http://localhost:5173` - you should see the dashboard!

### Build for Production
```bash
npm run build
```

Output will be in `dist/` folder.

## 🏗️ Project Structure
```
src/
├── views/                    # Page-level components
│   └── Dashboard/
│       ├── Dashboard.vue     # Main dashboard page (template)
│       ├── Dashboard.js      # Dashboard logic
│       └── Dashboard.css     # Dashboard styles
├── components/               # Reusable components
│   ├── TaskList/
│   │   ├── TaskList.vue      # Task management component
│   │   ├── TaskList.js       # Task list logic
│   │   └── TaskList.css      # Task list styles
│   ├── Calendar/
│   │   ├── Calendar.vue      # Calendar component
│   │   ├── Calendar.js       # Calendar logic
│   │   └── Calendar.css      # Calendar styles
│   └── Weather/
│       ├── Weather.vue       # Weather widget
│       ├── Weather.js        # Weather logic
│       └── Weather.css       # Weather styles
├── services/                 # API service layer
│   ├── taskService.js        # Task API calls
│   ├── eventService.js       # Event API calls
│   └── weatherService.js     # Weather API calls
├── App.vue                   # Root component
├── main.js                   # Application entry point
└── style.css                 # Global styles
```

## 📦 Component Overview

### Dashboard (View)
Main page component that organizes all widgets.

**Location:** `src/views/Dashboard/`

**Contains:**
- Weather widget
- Calendar widget  
- Task list

### TaskList (Component)
Manages user tasks with full CRUD operations.

**Location:** `src/components/TaskList/`

**Features:**
- Add new tasks (with optional due dates)
- Mark tasks complete/incomplete
- Delete tasks
- Real-time sync with backend

**API Endpoints Used:**
- `GET /api/tasks` - Load all tasks
- `POST /api/tasks` - Create task
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task

### Calendar (Component)
Weekly calendar view showing events and tasks.

**Location:** `src/components/Calendar/`

**Features:**
- 7-day week view
- Navigate between weeks
- Shows events as colored time blocks
- Shows tasks with due dates as badges
- Highlights current day
- Click events/tasks to view details

**API Endpoints Used:**
- `GET /api/events` - Load events
- `GET /api/tasks/with-deadlines` - Load tasks with due dates

**Display Logic:**
- **Events**: Full time blocks with color and location
- **Tasks**: Small badges/indicators
- **Today**: Highlighted with green border

### Weather (Component)
Displays current weather information.

**Location:** `src/components/Weather/`

**Features:**
- Current temperature and conditions
- "Feels like" temperature
- Humidity and wind speed
- Weather icon
- Location display

**API:** OpenWeatherMap (called directly from frontend)

**Configuration:**
Edit `src/services/weatherService.js` to change:
- API key
- Default city

## 🔌 API Integration

### Service Layer Pattern

All API calls are abstracted into service modules:

**Example: taskService.js**
```js
import axios from 'axios'

const API_URL = 'http://localhost:8080/api/tasks'

export default {
  getAllTasks() {
    return axios.get(API_URL)
  },
  createTask(task) {
    return axios.post(API_URL, task)
  },
  // ... more methods
}
```

**Used in components:**
```js
import taskService from '../../services/taskService'

async fetchTasks() {
  const response = await taskService.getAllTasks()
  this.tasks = response.data
}
```

**Benefits:**
- Centralized API logic
- Easy to update endpoints
- Reusable across components
- Clean separation of concerns

### API Configuration

Backend API URL is hardcoded in service files:
```js
const API_URL = 'http://localhost:8080/api/tasks'
```

**For production:** Update to your deployed backend URL.

## 🎨 Styling Approach

### Component-Scoped CSS
Each component has its own CSS file with scoped styles:
```vue
<template>
  <div class="task-list">
    ...
  </div>
</template>

<style src="./TaskList.css" scoped></style>
```

**Scoped** means styles only apply to this component.

### Global Styles
Common styles in `src/style.css`:
- CSS resets
- Body background
- Font family

### Color Scheme
```css
/* Primary colors */
Green (#4CAF50)   - Success, actions, primary buttons
Orange (#FF9800)  - Tasks, warnings, due dates
Red (#F44336)     - Errors, delete actions

/* Neutrals */
#F5F5F5 - Page background
#FFFFFF - Card/widget background  
#333333 - Primary text
#666666 - Secondary text
#DDDDDD - Borders
```

### Layout
- **Dashboard**: Vertical stack (mobile-first)
- **Calendar**: CSS Grid (7 columns for days)
- **Task List**: Flexbox (vertical list)
- **Cards**: Rounded corners (8px), subtle shadows

## 🧪 Development

### Hot Reload
Changes to `.vue`, `.js`, or `.css` files automatically reload in browser.

### Browser DevTools
Press **F12** to:
- Inspect elements
- View console logs
- Debug network requests
- Test responsive design

### Vue DevTools
Install Vue DevTools browser extension for:
- Component tree inspection
- State management
- Performance profiling

## 🚀 Deployment

### Vercel (Recommended)

1. **Push to GitHub**
```bash
git add .
git commit -m "Initial commit"
git push origin main
```

2. **Import to Vercel**
- Go to https://vercel.com
- Click "New Project"
- Import your GitHub repository
- Vercel auto-detects Vite configuration

3. **Configure Environment**
- Framework Preset: Vite
- Build Command: `npm run build`
- Output Directory: `dist`

4. **Deploy**
- Click "Deploy"
- Get URL: `https://your-dashboard.vercel.app`

5. **Update API URLs**
After deploying backend, update service files:
```js
const API_URL = 'https://your-backend.onrender.com/api/tasks'
```

### Other Options
- **Netlify** - Similar to Vercel
- **Render** - Can host frontend too
- **GitHub Pages** - Free, but static only

## 🐛 Troubleshooting

### CORS Errors
**Error:** "Blocked by CORS policy"

**Solution:** 
1. Verify backend is running
2. Check backend CORS config includes `http://localhost:5173`
3. Restart backend after CORS changes

### API Connection Failed
**Error:** "Network Error" or "Failed to load"

**Solution:**
1. Check backend is running at `http://localhost:8080`
2. Test endpoint in browser: `http://localhost:8080/api/tasks`
3. Check browser console for exact error

### Component Not Rendering
**Error:** Blank page or component missing

**Solution:**
1. Check browser console for errors
2. Verify component import path is correct
3. Check component is registered in parent

### Port Already in Use
**Error:** Port 5173 is already in use

**Solution:**
```bash
# Kill process on port 5173
lsof -i :5173
kill -9 <PID>

# Or run on different port
npm run dev -- --port 3000
```

## 📝 Development Notes

### Adding New Components

1. **Create component folder** in `src/components/`
```
NewComponent/
├── NewComponent.vue
├── NewComponent.js
└── NewComponent.css
```

2. **Component structure**
```vue
<template>
  <!-- HTML here -->
</template>

<script src="./NewComponent.js"></script>
<style src="./NewComponent.css" scoped></style>
```

3. **Import in parent**
```js
import NewComponent from '../../components/NewComponent/NewComponent.vue'

export default {
  components: {
    NewComponent
  }
}
```

### Code Conventions

- **Components**: PascalCase (`TaskList.vue`)
- **Files**: Match component name
- **Props**: camelCase
- **Events**: kebab-case (`@task-completed`)
- **API calls**: async/await pattern
- **Error handling**: try/catch with user feedback

### State Management
Currently using **component-local state** (Vue's `data()`).

For more complex state, consider:
- **Pinia** - Vue's official state management
- **Vuex** - Older but still popular

Not needed for this project size.

## 🔑 Environment Variables

### Weather API Key
Located in `src/services/weatherService.js`:
```js
const API_KEY = 'YOUR_API_KEY_HERE'
```

**To change:**
1. Get key from https://openweathermap.org
2. Update `API_KEY` in `weatherService.js`
3. Restart dev server

**Note:** API key is visible in frontend code (this is fine for free-tier APIs with rate limits).

## 📚 Useful Commands
```bash
# Install dependencies
npm install

# Start dev server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview

# Lint/format (if configured)
npm run lint
```

## 🤝 Contributing

This is a personal project, but suggestions welcome!

## 📄 License

This project is for personal use.

## 👤 Author

Quan Luu

## 🔗 Related

- [Backend Repository](../dashboard) - Spring Boot API
- [API Documentation](../dashboard#api-endpoints) - Endpoint reference