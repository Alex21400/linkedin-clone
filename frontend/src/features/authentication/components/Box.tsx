import React, { ReactNode } from 'react'

type BoxProps = {
    children: ReactNode,
    signUp?: boolean
}

const Box: React.FC<BoxProps> = ({ children, signUp }) => {
  return (
    <div className={`${signUp ? 'w-[25rem]' : 'w-[22rem]'} p-6 my-0 mx-auto bg-white rounded-md shadow-box`}>
        {children}
    </div>
  )
}

export default Box