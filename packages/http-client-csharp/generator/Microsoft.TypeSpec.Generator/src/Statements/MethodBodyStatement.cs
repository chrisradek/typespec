// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

using System.Collections.Generic;
using System.Diagnostics;

namespace Microsoft.TypeSpec.Generator.Statements
{
    [DebuggerDisplay("{GetDebuggerDisplay(),nq}")]
    public abstract class MethodBodyStatement
    {
        internal abstract void Write(CodeWriter writer);
        public static implicit operator MethodBodyStatement(MethodBodyStatement[] statements) => new MethodBodyStatements(statements);
        public static implicit operator MethodBodyStatement(List<MethodBodyStatement> statements) => new MethodBodyStatements(statements);

        private class PrivateEmptyLineStatement : MethodBodyStatement
        {
            internal override void Write(CodeWriter writer)
            {
                writer.WriteLine();
            }
        }

        private class PrivateEmptyStatement : MethodBodyStatement
        {
            internal override void Write(CodeWriter writer)
            {
                // Do nothing
            }
        }

        public static readonly MethodBodyStatement Empty = new PrivateEmptyStatement();
        public static readonly MethodBodyStatement EmptyLine = new PrivateEmptyLineStatement();

        public string ToDisplayString() => GetDebuggerDisplay();

        public IEnumerable<MethodBodyStatement> Flatten()
        {
            if (this is MethodBodyStatements statements)
            {
                foreach (var statement in statements.Statements)
                {
                    foreach (var subStatement in statement.Flatten())
                    {
                        yield return subStatement;
                    }
                }
            }
            else
            {
                yield return this;
            }
        }

        private string GetDebuggerDisplay()
        {
            using CodeWriter writer = new CodeWriter();
            Write(writer);
            return writer.ToString(false);
        }
    }
}
