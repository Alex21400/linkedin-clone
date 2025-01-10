import React, { ButtonHTMLAttributes } from 'react'

type ButtonProps = ButtonHTMLAttributes<HTMLButtonElement> & {
    outline?: boolean;
}

const Button: React.FC<ButtonProps> = ({ outline, children, ...otherProps }) => {
  return (
    <button className={`${outline ? 'bg-white border border-[rgba(0,0,0,0.6)] p-4 font-medium' : 'bg-primary text-white'} 
    flex justify-center items-center w-full px-4 py-3 rounded-full font-medium mt-4 mb-4 transition-all disabled:text-black disabled:bg-gray-400 disabled:cursor-not-allowed`}
        {...otherProps}>
            {children}
    </button>
  )
}

export default Button