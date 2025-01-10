import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Feed from './features/feed/pages/Feed'
import SignUp from './features/authentication/pages/SignUp'
import SignIn from './features/authentication/pages/SignIn'
import VerifyEmail from './features/authentication/pages/VerifyEmail'
import ResetPassword from './features/authentication/pages/ResetPassword'
import AuthenticationContextProvider from './features/authentication/context/AuthenticationContextProvider'

function App() {
  return (
    <Router>
      <Routes>
        <Route element={<AuthenticationContextProvider />}>
          <Route path='/authentication/sign-up' element={<SignUp />} />
          <Route path='/authentication/sign-in' element={<SignIn />} />
          <Route path='/authentication/request-password-reset' element={<ResetPassword />} />
          <Route path='/authentication/verify-email' element={<VerifyEmail />} />
          <Route path='/' element={<Feed />} /> 
        </Route>
      </Routes>
    </Router>
  )
}

export default App
