import React, { FormEvent, useState } from 'react'
import Layout from '../components/Layout'
import Box from '../components/Box'
import Input from '../components/Input'
import Button from '../components/Button'
import { useNavigate } from 'react-router-dom'

const ResetPassword: React.FC = () => {
  const [emailSent, setEmailSent] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [email, setEmail] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [passwordResetToken, setpasswordResetToken] = useState("");
  
  const navigate = useNavigate();
  
  const sendPasswordResetToken = async (email: string) => {
    try {
      const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/authentication/send-password-reset-token?email=${email}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json"
        }
      });

      if(response.ok) {
        setErrorMessage("");
        setEmailSent(true);
        return;
      }

      const { message } = await response.json();
      setErrorMessage(message);
    } catch(e) {
      console.log(e);
      setErrorMessage("Something went wrong, please try again");
    } finally {
      setIsLoading(false);
    }
  }

  const resetPassword = async (email: string, passwordResetToken: string, newPassword: string) => {
    try {
      const response = await fetch(`${import.meta.env.VITE_BACKEND_URL}/authentication/reset-password?email=${email}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ passwordResetToken, newPassword })
      });

      if(response.ok) {
        setErrorMessage("");
        navigate("/authentication/sign-in");
      }

      const { message } = await response.json();
      setErrorMessage(message);
    } catch(e) {
      console.log(e);
      setErrorMessage("Something went wrong, please try again");
    } finally {
      setIsLoading(false);
    }
  }

  const handleSendResetPasswordToken = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    await sendPasswordResetToken(email);
    setIsLoading(false);
  }

  const handleResetPassword = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    await resetPassword(email, passwordResetToken, newPassword);
    setIsLoading(false);
  }

  return (
    <Layout>
      <Box>
        <h2 className='text-3xl font-semibold mb-4'>Forgot Password</h2>
        {!emailSent ? (
          <form onSubmit={handleSendResetPasswordToken}>
            <Input
              label='Email'
              name='email'
              type='email'
              value={email}
              onChange={(e) => setEmail(e.target.value)}>
            </Input>
            {errorMessage && <p className='text-red-500 mb-4'>{errorMessage}</p>}
            <p className='text-sm'>
              We’ll send a verification code to this email if it matches an existing LinkedIn account.
            </p>
            <Button type='submit' disabled={isLoading}>
              Next
            </Button>
            <Button 
              type='button' 
              outline
              onClick={() => {
                navigate("/authentication/sign-in");
              }}>
              Back
            </Button>
          </form>
        ) : (
          <form onSubmit={handleResetPassword}>
            <p>Please enter the password reset code that we have sent to Your email and Your new password.</p>
            <Input
              label='Password reset code'
              name='passwordResetToken'
              key='passwordResetToken'
              type='text'
              value={passwordResetToken}
              onChange={(e) => setpasswordResetToken(e.target.value)}>
            </Input>
            <Input
              label='New Password'
              name='newPassword'
              key='newPassword'
              type='password'
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}>
            </Input>
            {errorMessage && <p className='text-red-500 mb-4'>{errorMessage}</p>}
            <Button type='submit' disabled={isLoading}>
              Reset Password
            </Button>
            <Button
              type='button'
              outline
              onClick={() => {
                setErrorMessage("");
                setEmailSent(false);
              }}
            >
              Back
            </Button>
          </form>
        )}
      </Box>
    </Layout>
  )
}

export default ResetPassword